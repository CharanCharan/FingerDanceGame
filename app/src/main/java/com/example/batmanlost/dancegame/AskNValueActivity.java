package com.example.batmanlost.dancegame;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity is the launcher activity. It asks user a value and starts the game
 */
public class AskNValueActivity extends AppCompatActivity {

    private EditText mUserInput;
    private Button mStartButton;
    private Button mQuickPlayButton;
    private TextView mNoOfTouches;
    private TextView mInputMismatchWarning;


    int noOfTouches = 0;   // ignore
    // user input value to generate N*N tiles
    private int N;

    /**
     *  Exports this activity's intent for explicit calling
     */
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext,AskNValueActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_value_n);

        // displays warning message if user enters wrong input
        mInputMismatchWarning = (TextView) findViewById(R.id.warning_input_mismatch);
        mInputMismatchWarning.setVisibility(View.INVISIBLE);

        // takes input from user
        mUserInput = (EditText) findViewById(R.id.user_input_value);
        mUserInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        // starts tiles activity with N*N tiles
        mStartButton = (Button) findViewById(R.id.start_game_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    N = Integer.valueOf(mUserInput.getText().toString());
                    startTilesActivity(N,noOfTouches);
                }catch (Exception e){
                    mInputMismatchWarning.setText(R.string.warning_input_mismatch);
                    mInputMismatchWarning.setVisibility(View.VISIBLE);
                }
            }
        });

        // starts tiles activity with 5*5 tiles
        mQuickPlayButton = (Button) findViewById(R.id.quick_play_button);
        mQuickPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTilesActivity(5,noOfTouches);
            }
        });

        // finds the touch screen feature and announces to the user
        mNoOfTouches = (TextView) findViewById(R.id.no_of_touches_allowed);
        mNoOfTouches.setVisibility(View.INVISIBLE);
        if (Integer.parseInt(Build.VERSION.SDK) >= 7) {
            PackageManager pm = getPackageManager();
            if (pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND)) {
                mNoOfTouches.setText(R.string.use_more_than_two);
                noOfTouches = 3;
            } else if (pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)){
                mNoOfTouches.setText(R.string.use_more_than_one);
                noOfTouches = 2;
            }
            else {
                mNoOfTouches.setText(R.string.use_one_finger);
                noOfTouches =1;
            }
            mNoOfTouches.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Starts Tiles activity with @param N number of tiles
     * Ignore @param numberOfTouches, couldn't make efficient use of it, still working on it
     * @param N
     * @param numberOfTouches
     */
    private void startTilesActivity(int N, int numberOfTouches){
        Intent intent = TilesActivity.newIntent(AskNValueActivity.this, N,numberOfTouches);
        startActivity(intent);
    }


}
