package cs.dawson.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ArrayList<ArrayList<Object>> imgs;
    ArrayList<ImageButton> btns;
    TextView question, scoreText, questionCounterText ;
    int incorrectCounter =0, correctCounter=0, questionCounter=0, correctAnswer;
    Button btnNext;
    boolean firstIncorrect=false;
    int incorrectId1, incorrectId2;

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

        /*SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        questionCounter = prefs.getInt("questionCounter", 0);
        correctCounter = prefs.getInt("correctCounter", 0 );
        incorrectCounter =  prefs.getInt("incorrectCounter", 0 );*/

        // && (savedInstanceState.getInt("questionCounter") != 0) && (savedInstanceState.getInt("correcteCounter") != 0) && (savedInstanceState.getInt("incorrecteCounter") != 0)
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

        /*if(prefs.getBoolean("paused", false)) {
            //restoring each of the four images+questions, + the correct answer
            ArrayList<ArrayList<Object>> restoredImgs = new ArrayList<>();
            restoredImgs.add(new ArrayList<Object>(Arrays.asList(
                    prefs.getInt("pic1", (int) imgs.get(0).get(0)),
                    prefs.getInt("pic1q", (int) imgs.get(0).get(1)))));
            restoredImgs.add(new ArrayList<Object>(Arrays.asList(
                    prefs.getInt("pic2", (int) imgs.get(1).get(0)),
                    prefs.getInt("pic2q", (int) imgs.get(1).get(1)))));
            restoredImgs.add(new ArrayList<Object>(Arrays.asList(
                    prefs.getInt("pic3", (int) imgs.get(2).get(0)),
                    prefs.getInt("pic3q", (int) imgs.get(2).get(1)))));
            restoredImgs.add(new ArrayList<Object>(Arrays.asList(
                    prefs.getInt("pic4", (int) imgs.get(3).get(0)),
                    prefs.getInt("pic4q", (int) imgs.get(3).get(1)))));

            btns.get(prefs.getInt("incorrectId2", incorrectId1)).setImageResource(R.drawable.incorrect);
            btns.get(prefs.getInt("incorrectId2", incorrectId2)).setImageResource(R.drawable.incorrect);
            setAllBtnListenerIncorrect();
            displayImages(restoredImgs,
                    prefs.getInt("correctAnswer", correctAnswer));

        }
        else
            nextQuestion(null);*/

        if(savedInstanceState != null) {
            if (savedInstanceState.getBoolean("paused", false)) {
                //restoring each of the four images+questions, + the correct answer
                ArrayList<ArrayList<Object>> restoredImgs = new ArrayList<>();
                restoredImgs.add(new ArrayList<Object>(Arrays.asList(
                        savedInstanceState.getInt("pic1", (int) imgs.get(0).get(0)),
                        savedInstanceState.getInt("pic1q", (int) imgs.get(0).get(1)))));
                restoredImgs.add(new ArrayList<Object>(Arrays.asList(
                        savedInstanceState.getInt("pic2", (int) imgs.get(1).get(0)),
                        savedInstanceState.getInt("pic2q", (int) imgs.get(1).get(1)))));
                restoredImgs.add(new ArrayList<Object>(Arrays.asList(
                        savedInstanceState.getInt("pic3", (int) imgs.get(2).get(0)),
                        savedInstanceState.getInt("pic3q", (int) imgs.get(2).get(1)))));
                restoredImgs.add(new ArrayList<Object>(Arrays.asList(
                        savedInstanceState.getInt("pic4", (int) imgs.get(3).get(0)),
                        savedInstanceState.getInt("pic4q", (int) imgs.get(3).get(1)))));

                btns.get(savedInstanceState.getInt("incorrectId2", incorrectId1)).setImageResource(R.drawable.incorrect);
                btns.get(savedInstanceState.getInt("incorrectId2", incorrectId2)).setImageResource(R.drawable.incorrect);
                setAllBtnListenerIncorrect();
                displayImages(restoredImgs,
                        savedInstanceState.getInt("correctAnswer", correctAnswer));

            } else
                nextQuestion(null);
        }
        else
            nextQuestion(null);
    }

    public void nextQuestion(View v)
    {
        setAllBtnListenerIncorrect();
        displayImages( imgs , randomizeImages());
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

    public int randomizeImages()
    {
        Collections.shuffle(imgs); // shuffle the choice images
        return new Random().nextInt(4); // get random number 0-4
    }

    public void displayImages(ArrayList<ArrayList<Object>> choice, int correctAnswer)
    {
        //set the 4 buttons to the first 4 images in the shuffled choices-array
        btns.get(0).setBackground(getResources().getDrawable((int)choice.get(0).get(0), null));
        btns.get(1).setBackground(getResources().getDrawable((int)choice.get(1).get(0), null));
        btns.get(2).setBackground(getResources().getDrawable((int)choice.get(2).get(0), null));
        btns.get(3).setBackground(getResources().getDrawable((int)choice.get(3).get(0), null));

        btns.get(0).setImageResource(0);
        btns.get(1).setImageResource(0);
        btns.get(2).setImageResource(0);
        btns.get(3).setImageResource(0);

        btns.get(0).setTag("1");
        btns.get(1).setTag("2");
        btns.get(2).setTag("3");
        btns.get(3).setTag("4");

        //choose a correct answer, then get that image's string and set it as the question
        question.setText((int)choice.get(correctAnswer).get(1));

        //set the correct answer's onClickListener
        btns.get(correctAnswer).setOnClickListener( new View.OnClickListener() {
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
            incorrectId2=(int)((ImageButton)v).getTag();
            btnNext.setVisibility(View.VISIBLE);
            questionCounter++;
        }
        else // first incorrect
        {
            incorrectId1=(int)((ImageButton)v).getTag();
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
        scoreText.setText(getString(R.string.scoreText1) + correctCounter + getString(R.string.scoreText2) +incorrectCounter + getString(R.string.scoreText3));
        questionCounterText.setText(getString(R.string.counterText1) + questionCounter + getString(R.string.counterText2));
        if (questionCounter == 10)
        {
            startActivityForResult(new Intent(this, ResultActivity.class), 9);
        }
    }

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
    }
  
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
        startActivity(new Intent(this, SearchActivity.class));
    }

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
        outState.putInt("incorrectId1", incorrectId1);
        outState.putInt("incorrectId2", incorrectId2);
    }

    public void onPause(){
        super.onPause();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("score", correctCounter);

        editor.commit();
    }
}
