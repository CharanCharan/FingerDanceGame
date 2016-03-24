package com.example.batmanlost.dancegame;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameResultActivity extends AppCompatActivity {

    // EXTRAS to store data in this activity's intents
    private static final String EXTRA_WINNER_NAME = "com.example.batmanlost.dancegame.winner_name";
    private static final String EXTRA_LOSER_NAME = "com.example.batmanlost.dancegame.loser_name";
    private static final String EXTRA_LOSER_LOST_BY = "com.example.batmanlost.dancegame.loser_lost_by";

    // Strings to store the type of mistake committed by the player
    public static final String sLostByLiftingHandFirst = "LostByLiftingHandFirst";
    public static final String sLostByPressingWrongTile = "LostByPressingWrongTile";

    private TextView mDisplayWinner;
    private TextView mDisplayLoser;
    private Button mReplayGameButton;

    /**
     * Exports this activity's intent with required EXTRAS for explicit calling
     * @param packageContext
     * @param winnerName
     * @param loserName
     * @param lostBy
     * @return
     */
    public static Intent newIntent(Context packageContext, String winnerName, String loserName, String lostBy){
        Intent intent = new Intent(packageContext,GameResultActivity.class);
        intent.putExtra(EXTRA_WINNER_NAME,winnerName);
        intent.putExtra(EXTRA_LOSER_NAME,loserName);
        intent.putExtra(EXTRA_LOSER_LOST_BY, lostBy);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        // display the winner name
        mDisplayWinner = (TextView) findViewById(R.id.display_winner);
        String winnerName = getIntent().getStringExtra(EXTRA_WINNER_NAME);

        // dynamically update and use string from resources
        Resources res = getResources();
        String winnerText = String.format(res.getString(R.string.announce_winner),winnerName);

        mDisplayWinner.setText(winnerText);

        // similary display loser name and the mistake
        mDisplayLoser = (TextView) findViewById(R.id.display_loser);
        String loserName = getIntent().getStringExtra(EXTRA_LOSER_NAME);

        // get the mistake from intent extra
        String lostBy = getIntent().getStringExtra(EXTRA_LOSER_LOST_BY) ;
        // assign appropriate string
        String loserText ="";
        if (lostBy.equals(sLostByLiftingHandFirst)){
            loserText = String.format(res.getString(R.string.announce_loser_hand_lifted), loserName);
        }else {
            loserText = String.format(res.getString(R.string.announce_loser_pressed_wrong), loserName);
        }
        mDisplayLoser.setText(loserText);

        // button to restart game
        mReplayGameButton = (Button) findViewById(R.id.replay_game_button);
        mReplayGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFingerDanceLaunchActivity();
            }
        });
    }

    /**
     * Restart game when user presses back button
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startFingerDanceLaunchActivity();
        finish();
    }

    /**
     * Starts Finger Dance launcher activity
     */
    private void startFingerDanceLaunchActivity(){
        Intent intent = AskNValueActivity.newIntent(GameResultActivity.this);
        startActivity(intent);
    }


}
