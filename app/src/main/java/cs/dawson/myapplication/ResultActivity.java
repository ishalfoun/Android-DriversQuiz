package cs.dawson.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * @author Isaak Shalfoun
 * @version 10-10-2017
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    public void playAgain(View v)
    {
        Intent i = new Intent();
        setResult(0);
        finish();
    }
}
