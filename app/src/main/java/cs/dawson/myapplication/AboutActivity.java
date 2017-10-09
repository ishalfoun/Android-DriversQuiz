package cs.dawson.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 *
 */

public class AboutActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        TextView score = (TextView)findViewById(R.id.tvDisplayScores);
        int temp = prefs.getInt("score", 0);
        score.setText(""+temp);

        /*Intent intent = getIntent();
        TextView score = (TextView)findViewById(R.id.tvDisplayScores);
        int temp = intent.getIntExtra("correctCounter", 0);
        score.setText(""+temp);*/
    }

}
