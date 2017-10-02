package cs.dawson.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    ArrayList<ArrayList<Object>> imgs;
    ArrayList<ImageButton> btns;
    TextView question, scoreText, questionCounterText ;
    int incorrectCounter =0, correctCounter=0, questionCounter=0;
    Button btnNext;
    boolean firstIncorrect=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = (Button) findViewById(R.id.btnNext);

        scoreText = (TextView) findViewById(R.id.ScoreText);
        questionCounterText = (TextView) findViewById(R.id.questionCountText);

        //buttons
        btns = new ArrayList<>();
            btns.add((ImageButton) findViewById(R.id.btnTopLeft));
            btns.add((ImageButton) findViewById(R.id.btnTopRight));
            btns.add((ImageButton) findViewById(R.id.btnBotLeft));
            btns.add((ImageButton) findViewById(R.id.btnBotRight));

        //question
        question = (TextView)findViewById(R.id.question);

        //fill an array with Pairs of R.drawable & R.string (Picture & Question)
        imgs = new ArrayList<>();
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic01, R.string.question01) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic02, R.string.question02) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic03, R.string.question03) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic04, R.string.question04) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic05, R.string.question05) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic06, R.string.question06) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic07, R.string.question07) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic08, R.string.question08) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic09, R.string.question09) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic10, R.string.question10) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic11, R.string.question11) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic12, R.string.question12) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic13, R.string.question13) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic14, R.string.question14) ) );
            imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic15, R.string.question15) ) );

        nextQuestion(null);
        Drawable d = getResources().getDrawable( R.drawable.pic01 );
    }

    public void nextQuestion(View v)
    {
        setAllBtnListenerIncorrect();
        setRandomImages( imgs );

        firstIncorrect=false;
        btnNext.setVisibility(View.INVISIBLE);
    }

    public void setAllBtnListenerIncorrect()
    {
        for (ImageButton btn : btns ) {
            btn.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    incorrect(v);
                }
            });
        }
    }
    public void setAllBtnListenerOff()
    {
        for (ImageButton btn : btns ) {
            btn.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                }
            });
        }
    }

    public void setRandomImages(ArrayList<ArrayList<Object>> choice)
    {
        Collections.shuffle(choice); // shuffle the choice images
        int correctAnswer = new Random().nextInt(4); // get random number 0-4

        //set the 4 buttons to the first 4 images in the shuffled choices-array
        btns.get(0).setBackground(getResources().getDrawable((int)choice.get(0).get(0), null));
        btns.get(1).setBackground(getResources().getDrawable((int)choice.get(1).get(0), null));
        btns.get(2).setBackground(getResources().getDrawable((int)choice.get(2).get(0), null));
        btns.get(3).setBackground(getResources().getDrawable((int)choice.get(3).get(0), null));

        btns.get(0).setImageResource(0);
        btns.get(1).setImageResource(0);
        btns.get(2).setImageResource(0);
        btns.get(3).setImageResource(0);

        //choose a correct answer, then get that image's string and set it as the question
        question.setText((int)choice.get(correctAnswer).get(1));

        //set the correct answer's onClickListener
        btns.get(correctAnswer).setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                correct(v);
            }
        });
    }

    public void correct(View v)
    {
        question.setText("CORRECT!!!");
        correctCounter++;
        questionCounter++;
        setAllBtnListenerOff();

        ((ImageButton) v).setImageResource(R.drawable.correct);//set img src to incorrect

        btnNext.setVisibility(View.VISIBLE);
        updateCounters();

    }

    public void incorrect(View v)
    {
        ((ImageButton) v).setImageResource(R.drawable.incorrect);//set img src to incorrect
        incorrectCounter++;

        if (firstIncorrect) // second incorrect
        {
            setAllBtnListenerOff();
            btnNext.setVisibility(View.VISIBLE);
            questionCounter++;
        }
        else // first incorrect
        {
            ((ImageButton) v).setOnClickListener(new View.OnClickListener() { //set img to not clickable.
                public void onClick(View v) {
                }
            });
            firstIncorrect = true;
        }
        updateCounters();
    }

    public void updateCounters()
    {
        scoreText.setText("Your score is: "+ correctCounter + " Correct/ "+incorrectCounter + " Incorrect");
        questionCounterText.setText("You have completed " + questionCounter + " out of 10 questions");
        if (questionCounter == 10)
        {
            startActivity(new Intent(this, ResultActivity.class));
        }
    }

}
