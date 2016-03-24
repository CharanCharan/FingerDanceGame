package com.example.batmanlost.dancegame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by BatmanLost on 22-03-2016.
 */
public class TilesActivity extends SingleFragmentActivity {

    // EXTRAS
    private static final String EXTRA_USER_INPUT_VALUE_N = "com.example.batmanlost.dancegame.noOfTiles";
    private static final String EXTRA_MAX_NUMBER_OF_TOUCHES = "com.example.batmanlost.dancegame.max_no_of_touches"; //IGNORE

    /**
     * Exports this Activity's intent with desired EXTRAS
     * @param packageContext
     * @param noOfTiles
     * @param maxNoOfTouchesAllowed
     * @return
     */
    public static Intent newIntent(Context packageContext, int noOfTiles, int maxNoOfTouchesAllowed){
        Intent intent = new Intent(packageContext,TilesActivity.class);
        intent.putExtra(EXTRA_USER_INPUT_VALUE_N,noOfTiles);
        intent.putExtra(EXTRA_MAX_NUMBER_OF_TOUCHES,maxNoOfTouchesAllowed); // ignore
        return intent;
    }

    /**
     * Updates this activity's single fragment class with necessary details using intent EXTRAS
     * @return
     */
    @Override
    protected Fragment createFragment() {
        int noOfTiles = getIntent().getIntExtra(EXTRA_USER_INPUT_VALUE_N,0);
        int maxNoOfTouches = getIntent().getIntExtra(EXTRA_MAX_NUMBER_OF_TOUCHES,0); // ignore
        return TileGridFragment.newInstance(noOfTiles,maxNoOfTouches);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hides the toolbar
        getSupportActionBar().hide();
        // sets device view to full screen - from stack overflow
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
