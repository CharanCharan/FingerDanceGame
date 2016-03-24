package com.example.batmanlost.dancegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment holds all tiles
 * Created by BatmanLost on 22-03-2016.
 */
public class TileGridFragment extends Fragment{

    // no of tiles to be created
    private int mNoOfTiles = 0;
    // refree for the game
    private Referee mReferee = Referee.twoPlayerGame();
    // recycler view to hold tiles
    private RecyclerView mRecyclerView;
    // adapter for recycler view
    private TileAdapter mTileAdapter;

    /**
     * Returns a new Instance of this fragment
     * @param noOfTiles
     * @param maxNoOfTouhesAllowed
     * @return
     */
    public static TileGridFragment newInstance(int noOfTiles,int maxNoOfTouhesAllowed){
        TileGridFragment tileListFragment = new TileGridFragment();
        tileListFragment.setNoOfTiles(noOfTiles);
        tileListFragment.mReferee.setMaxNoOfTouches(maxNoOfTouhesAllowed); // ignore
        return tileListFragment;
    }

    /**
     * Initializes the no of tiles to be created
     * @param noOfTiles
     */
    public void setNoOfTiles(int noOfTiles) {
        mNoOfTiles = noOfTiles;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retains instance for configuration change
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflate the correponding fragment layout
        View view = inflater.inflate(R.layout.fragment_tile_grid,container,false);

        // create adapter for recycler view
        mTileAdapter = new TileAdapter(Tile.getTiles(mNoOfTiles*mNoOfTiles));

        // initialize recycler view with the corresponding adapter
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_tile_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),mNoOfTiles));
        mRecyclerView.setAdapter(mTileAdapter);

        // highlight a random tile
        updateUI();

        return view;
    }

    /**
     * Highlights a random tile every time and displays next player's name
     */
    private void updateUI() {
        Tile.resetHighlightTile();
        int position = Tile.getHighlightedTileIndex();
        mTileAdapter.notifyItemChanged(position);

        String playerNameToPlayNow = mReferee.getCurrentPlayerName()+"'s turn";
        Toast.makeText(getActivity(),playerNameToPlayNow,Toast.LENGTH_SHORT).show();
    }

    // adapter with tile holder
    private class TileAdapter extends RecyclerView.Adapter<TileHolder>{

        // bind data from these tiles
        private List<Tile> mTiles;

        public TileAdapter(List<Tile> tiles) {
            mTiles = tiles;
        }

        @Override
        public TileHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new TileHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(TileHolder holder, int position) {
            Tile tile = mTiles.get(position);
            // bind tile's color view
            holder.bindColor(tile);
            // highlight tile if required
            if (tile.isToBeHighlighted()){ holder.highLightButton();}
        }

        @Override
        public int getItemCount() {
            return mTiles.size();
        }

    }

    private class TileHolder extends RecyclerView.ViewHolder {

        // view has a simple button
        private Button mTileButton;
        // stash tile with it's button
        private Tile mTile;

        public TileHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.grid_item_tile,container,false));
            mTileButton = (Button) itemView.findViewById(R.id.grid_item_tile_button);
            // set height of button at run time
            mTileButton.setLayoutParams (new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, getButtonHeight()));
            mTileButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // when a tile/button is touched
                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                        //if player hasn't touched the highlighted tile
                        if ( !mReferee.playerHasPressedHighlightedTile((mTile))){
                            launchResultActivity(GameResultActivity.sLostByPressingWrongTile);
                        }
                        // set this tile hilghlight status to false
                        mTile.setHighlightedToFalse();
                        // remove the highlight effect when clicked by clearing animation on button
                        mTileButton.clearAnimation();
                        // highlight a random tile
                        updateUI();
                    }
                    // when un touched
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        if( !mReferee.isGameFinished()){
                            // code to check if player is using specified no of fingers
/*                            if (!mReferee.playerHasUnPressedCorrectTile(mTile)){
                                launchResultActivity(GameResultActivity.sLostByLiftingHandFirst);
                            }*/
                            mReferee.tileUnPressed(mTile);
                            launchResultActivity(GameResultActivity.sLostByLiftingHandFirst);
                        }
                    }
                    return true;
                }
            });
        }

        /**
         * Displays result of this game
         * @param lostBy
         */
        private void launchResultActivity(String lostBy){
            Intent intent = GameResultActivity.newIntent(getActivity(), mReferee.getWinnerName(),mReferee.getLoserrName(),lostBy);
            startActivity(intent);
        }

        /**
         * Sets the button color to tile color
         * @param tile
         */
        public void bindColor(Tile tile){
            mTile = tile;
            mTileButton.setBackgroundColor(tile.getTileColor());
        }

        /**
         * Highlights the button
         */
        public void highLightButton(){ mTileButton.setAnimation(getAnimation());}

        /**
         * Button height to update at run time
         * @return
         */
        private int getButtonHeight(){
            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            int height = metrics.heightPixels;
            int btnHeight = height/mNoOfTiles;
            return btnHeight;
        }

        /**
         * Animation of the button
         * Code taken from stack overflow
         * http://stackoverflow.com/a/4852468/4972717
         * @return
         */
        private Animation getAnimation(){
            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
            animation.setDuration(250); // duration - half a second
            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
            animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
            return animation;
        }
    }
}
