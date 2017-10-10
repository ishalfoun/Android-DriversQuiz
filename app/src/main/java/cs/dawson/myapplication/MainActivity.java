package cs.dawson.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ArrayList<ArrayList<Object>> imgs;
    ArrayList<ArrayList<Object>> qimgs;
    ArrayList<ImageButton> btns;
    TextView question, scoreText, questionCounterText ;
    int incorrectCounter =0, correctCounter=0, questionCounter=0, correctAnswer;
    Button btnNext;
    boolean firstIncorrect=true;
    int incorrectId1, incorrectId2;

    int rand;
    ArrayList<Integer> qlist;

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
        if((savedInstanceState != null)) {
            questionCounter = savedInstanceState.getInt("questionCounter");
            correctCounter = savedInstanceState.getInt("correctCounter");
            incorrectCounter = savedInstanceState.getInt("incorrectCounter");
        }
        updateCounters();


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
        imgs.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic15, R.string.question15) ) );


        qimgs = new ArrayList<>();

        //qlist = new ArrayList<Object>();

        nextQuestion(null);
    }


    public void nextQuestion(View v)
    {
        setAllBtnListenerIncorrect();
        displayImages(/* imgs , randomizeImages()*/);
        firstIncorrect=true;
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

    public int randomizeImages()
    {
        Collections.shuffle(imgs); // shuffle the choice images
        return new Random().nextInt(4); // get random number 0-3
    }

    public void displayImages()
    {
        for (int i=0; i<=3; i++) //loop four times
        {
            rand = new Random().nextInt(imgs.size());
            qimgs.add(imgs.get(rand)); //add random q into qlist;
            imgs.remove(rand); //remove that q from imgs


        }
        //now we have 4 qs.

        Log.d("MYTYPE" ,  "qimgs size: " + qimgs.size());
        Log.d("MYTYPE" ,  "imgs size: " + imgs.size());
        //set the 4 buttons to the 4 qs
        btns.get(0).setBackground(getResources().getDrawable((int)qimgs.get(0).get(0), null));
        btns.get(1).setBackground(getResources().getDrawable((int)qimgs.get(1).get(0), null));
        btns.get(2).setBackground(getResources().getDrawable((int)qimgs.get(2).get(0), null));
        btns.get(3).setBackground(getResources().getDrawable((int)qimgs.get(3).get(0), null));

        //remove overlaying pictures
        btns.get(0).setImageResource(0);
        btns.get(1).setImageResource(0);
        btns.get(2).setImageResource(0);
        btns.get(3).setImageResource(0);

        //choose a correct answer, then get that image's string and set it as the question
        rand = new Random().nextInt(4);
        question.setText((int)qimgs.get(rand).get(1));

        //set the correct answer's onClickListener
        btns.get(rand).setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                correct(v);
            }
        });
    }

    /*@Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    }*/

    public void correct(View v)
    {

        qimgs.remove(rand); // remove this question forever

        //move the other answers back into usable pool
        imgs.add(qimgs.get(0));
        imgs.add(qimgs.get(1));
        imgs.add(qimgs.get(2));
        qimgs.remove(0);
        qimgs.remove(0);
        qimgs.remove(0);

        correctCounter++;
        questionCounter++;
        setAllBtnListenerOff();
        ((ImageButton) v).setImageResource(R.drawable.correct);//mark img as correct
        btnNext.setVisibility(View.VISIBLE);
        updateCounters();

        Log.d("MYTYPE" ,  "qimgs size: " + qimgs.size());
        Log.d("MYTYPE" ,  "imgs size: " + imgs.size());    }

    public void incorrect(View v)
    {
        ((ImageButton) v).setImageResource(R.drawable.incorrect);// mark img as incorrect

        ((ImageButton) v).setOnClickListener(new View.OnClickListener() { //turn off click listener
            public void onClick(View v) {  }
        });

        incorrectCounter++;

        if (firstIncorrect) // if this is the first incorrect
            firstIncorrect = false;
        else // if this is second incorrect
        {
            setAllBtnListenerOff();
            btnNext.setVisibility(View.VISIBLE);
            questionCounter++;

            //put all qs back into pool
            imgs.add(qimgs.get(0));
            imgs.add(qimgs.get(1));
            imgs.add(qimgs.get(2));
            imgs.add(qimgs.get(3));
            qimgs.remove(0);
            qimgs.remove(0);
            qimgs.remove(0);
            qimgs.remove(0);
        }
        updateCounters();

        Log.d("MYTYPE" ,  "qimgs size: " + qimgs.size());
        Log.d("MYTYPE" ,  "imgs size: " + imgs.size());}

    public void updateCounters()
    {
        scoreText.setText(getString(R.string.scoreText1) + correctCounter + getString(R.string.scoreText2) +incorrectCounter + getString(R.string.scoreText3));
        questionCounterText.setText(getString(R.string.counterText1) + questionCounter + getString(R.string.counterText2));
        if (questionCounter == 10)
        {
            startActivityForResult(new Intent(this, ResultActivity.class), 9);
        }
    }
/*
    @Override
    public void onActivityResult(int request, int result, Intent data)
    {
        if (request==9)
        {
            if (result==0)
            {
                questionCounter = 0;
                correctCounter = 0;
                incorrectCounter = 0;
                updateCounters();
                nextQuestion(null);
            }
        }
    }*/

    /*public void onPause()
    {
        super.onPause();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.putInt("questionCounter" , questionCounter);
        editor.putInt("correctCounter" , correctCounter );
        editor.putInt("incorrectCounter" , incorrectCounter );
        editor.putInt("questionCounter" , questionCounter );
        editor.putInt("correctCounter" , correctCounter );
        editor.putInt("incorrectCounter" , incorrectCounter );
        //saving each of the four images+questions, + the correct answer
        editor.putInt("pic1", (int)imgs.get(0).get(0) );
        editor.putInt("pic1q", (int)imgs.get(0).get(1) );
        editor.putInt("pic2", (int)imgs.get(1).get(0) );
        editor.putInt("pic2q", (int)imgs.get(1).get(1) );
        editor.putInt("pic3", (int)imgs.get(2).get(0) );
        editor.putInt("pic3q", (int)imgs.get(2).get(1) );
        editor.putInt("pic4", (int)imgs.get(3).get(0) );
        editor.putInt("pic4q", (int)imgs.get(3).get(1) );
        editor.putInt("correctAnswer", correctAnswer);
        editor.putBoolean("paused", true);
        editor.putInt("incorrectId1", incorrectId1);
        editor.putInt("incorrectId2", incorrectId2);
        editor.commit();
    }*/

    public void aboutGameInfo(View v)
    {
        //startActivity(new Intent(this, AboutActivity.class));
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("correctCounter", correctCounter);
        startActivity(intent);
    }

    public void hintSearch(View v)
    {
        TextView question = (TextView)findViewById(R.id.question);
        String q = question.getText().toString();
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, "road sign " + q);
        startActivity(intent);
    }
/*
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("questionCounter" , questionCounter);
        outState.putInt("correctCounter" , correctCounter );
        outState.putInt("incorrectCounter" , incorrectCounter );
        outState.putInt("questionCounter" , questionCounter );
        outState.putInt("correctCounter" , correctCounter );
        outState.putInt("incorrectCounter" , incorrectCounter );

        //outState.putString("question", question.toString());
        outState.putInt("pic1", (int)imgs.get(0).get(0) );
        outState.putInt("pic1q", (int)imgs.get(0).get(1) );
        outState.putInt("pic2", (int)imgs.get(1).get(0) );
        outState.putInt("pic2q", (int)imgs.get(1).get(1) );
        outState.putInt("pic3", (int)imgs.get(2).get(0) );
        outState.putInt("pic3q", (int)imgs.get(2).get(1) );
        outState.putInt("pic4", (int)imgs.get(3).get(0) );
        outState.putInt("pic4q", (int)imgs.get(3).get(1) );
        outState.putInt("correctAnswer", correctAnswer);
        outState.putBoolean("paused", true);
       // outState.putInt("incorrectId1", incorrectId1);
       // outState.putInt("incorrectId2", incorrectId2);
    }

    public void onPause(){
        super.onPause();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("score", correctCounter);

        editor.commit();
    }*/
}
