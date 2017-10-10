package cs.dawson.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * @author Hannah Ly
 * @version 10-10-2017
 */

public class AboutActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        /*SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        TextView score = (TextView)findViewById(R.id.tvDisplayScores);
        int temp = prefs.getInt("score", 0);
        score.setText(""+temp);*/

        TextView score = (TextView)findViewById(R.id.tvDisplayScores);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        // get data via the key
        int value1 = extras.getInt("highscore");

            score.setText("" + value1);

    }

    @Override
    public void onPause(){
        super.onPause();
        int score;
        String temp;

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        TextView scoreTV = (TextView)findViewById(R.id.tvDisplayScores);
        temp = scoreTV.getText().toString();
        score = Integer.parseInt(temp);

        editor.putInt("highscore", score);
    }

}
