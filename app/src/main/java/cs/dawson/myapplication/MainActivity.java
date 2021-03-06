package cs.dawson.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Isaak Shalfoun and Hannah Ly
 * @version 10-10-2017
 *
 * The game consist of a driving quiz where you identified the image that corresponds to the given
 * description.
 * This class has all the logic for the game and fires other activities.
 */
public class MainActivity extends AppCompatActivity {

    ArrayList<ArrayList<Object>> questionPool;
    ArrayList<ArrayList<Object>> questionsUsed;
    ArrayList<ImageButton> btns;
    TextView question, scoreText, questionCounterText ;
    int incorrectCounter =0, savedhighscore, highscore, correctCounter=0, questionCounter=0, correctAnswer,
    falseAnswerSignal, btnListenerOff, incorrectId1, incorrectId2, rand;
    Button btnNext;
    boolean firstIncorrect=true;

    /**
     * This method is overriding the super onCreate class. It recreated the game depending on if
     * we are starting up the app again or if we rotated the screen.
     * @param savedInstanceState
     */
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

        btns.get(0).setTag(0);
        btns.get(1).setTag(1);
        btns.get(2).setTag(2);
        btns.get(3).setTag(3);

        //question
        question = (TextView)findViewById(R.id.question);

        questionsUsed = new ArrayList<>();

        //fill an array with Pairs of R.drawable & R.string (Picture & Question)
        questionPool = new ArrayList<>();
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic01, R.string.question01, 1) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic02, R.string.question02, 2) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic03, R.string.question03, 3) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic04, R.string.question04, 4) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic05, R.string.question05, 5) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic06, R.string.question06, 6) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic07, R.string.question07, 7) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic08, R.string.question08, 8) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic09, R.string.question09, 9) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic10, R.string.question10, 10) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic11, R.string.question11, 11) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic12, R.string.question12, 12) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic13, R.string.question13, 13) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic14, R.string.question14, 14) ) );
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic15, R.string.question15, 15) ) );
        //TODO: get a 16th picture and text
        questionPool.add( new ArrayList<Object>( Arrays.asList(R.drawable.pic15, R.string.question15, 16) ) );


        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        questionCounter=prefs.getInt("questionCounter" , questionCounter);
        correctCounter=prefs.getInt("correctCounter" , correctCounter );
        incorrectCounter=prefs.getInt("incorrectCounter" , incorrectCounter );
        savedhighscore = prefs.getInt("highscore",0);

        if((savedInstanceState != null)) {
            Log.d("MYTYPE", "loading now" );
            questionCounter = savedInstanceState.getInt("questionCounter");
            correctCounter = savedInstanceState.getInt("correctCounter");
            incorrectCounter = savedInstanceState.getInt("incorrectCounter");

            correctAnswer=savedInstanceState.getInt("qcorrect");

            falseAnswerSignal = savedInstanceState.getInt("falseAnswerSignal");

            Log.d("MYTYPE", " falseanswer " + falseAnswerSignal);
            if (falseAnswerSignal!=-1) {
                btns.get(falseAnswerSignal).setImageResource(R.drawable.incorrect);// mark img as incorrect
                firstIncorrect = false;
            }

            if (savedInstanceState.getBoolean("btnNextVisibility"))
            {
                nextQuestion(null);
                return;
            }
            ArrayList<Object> item;
            for(int i = 0; i< questionPool.size(); i++) //ArrayList<Object> item : questionPool)
            {
                item = questionPool.get(i);
                if ((int)item.get(2) == savedInstanceState.getInt("q1")) // if the id of the question matches the saved index
                {
                    questionsUsed.add(item);
                    questionPool.remove(item);
                    i--;
                }
                if ((int)item.get(2) == savedInstanceState.getInt("q2")) // if the id of the question matches the saved index
                {
                    questionsUsed.add(item);
                    questionPool.remove(item);
                    i--;
                }
                if ((int)item.get(2) == savedInstanceState.getInt("q3")) // if the id of the question matches the saved index
                {
                    questionsUsed.add(item);
                    questionPool.remove(item);
                    i--;
                }
                if ((int)item.get(2) == savedInstanceState.getInt("q4")) // if the id of the question matches the saved index
                {
                    questionsUsed.add(item);
                    questionPool.remove(item);
                    i--;
                }
            }
            btnListenerOff = savedInstanceState.getInt("btnListenerOff");

            Log.d("MYTYPE", "After loading: questionsUsed size= " + questionsUsed.size() );
            setAllBtnListenerIncorrect();
            displayImages();
        }
        else {
            btnListenerOff=-1;
            nextQuestion(null);
        }
        updateCounters();
    }

    /**
     * This method is used to set up the GUI for the next question.
     * @param v
     */
    public void nextQuestion(View v)
    {
        setAllBtnListenerIncorrect();
        get4newQuestions();        //now we have 4 qs.
        displayImages();
        firstIncorrect=true;
        btnNext.setVisibility(View.INVISIBLE);
    }

    /**
     *
     */
    public void setAllBtnListenerIncorrect()
    {
        for (ImageButton btn : btns ) {
            btn.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    incorrect(v);
                }
            });
        }

        Log.d("MYTYPE", " btnlisteneroff= " + btnListenerOff);
        if ( btnListenerOff!=-1) {
            btns.get(btnListenerOff).setOnClickListener(new View.OnClickListener() { //turn off click listener
                public void onClick(View v) {
                }
            });
        }
    }

    /**
     *
     */
    public void setAllBtnListenerOff()
    {
        for (ImageButton btn : btns ) {
            btn.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                }
            });
        }
    }

    /**
     * This method is overriding the super onPause method.
     * It is used when we want to save informations about our game when the app is not visible.
     */
    @Override
    public void onPause()
    {
        super.onPause();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.putInt("questionCounter" , questionCounter);
        editor.putInt("correctCounter" , correctCounter );
        editor.putInt("incorrectCounter" , incorrectCounter );

        if (highscore > savedhighscore)
            editor.putInt("highscore" , highscore );

        editor.commit();
    }

    /**
     * This method is used to get 4 new questions to display on the GUI.
     */
    public void get4newQuestions()
    {
        //remove overlaying pictures
        btns.get(0).setImageResource(0);
        btns.get(1).setImageResource(0);
        btns.get(2).setImageResource(0);
        btns.get(3).setImageResource(0);
        falseAnswerSignal=-1;
        btnListenerOff=-1;
        for (int i=0; i<=3; i++) //loop four times
        {
            rand = new Random().nextInt(questionPool.size());
            questionsUsed.add(questionPool.get(rand)); //add random q into qlist;
            questionPool.remove(rand); //remove that q from questionPool
        }
        correctAnswer = new Random().nextInt(4);
    }

    /**
     * This method is used to display the randomized pictures for the question in the appropriate
     * place.
     */
    public void displayImages()
    {
        //set the 4 buttons to the 4 qs
        btns.get(0).setBackground(getResources().getDrawable((int) questionsUsed.get(0).get(0), null));
        btns.get(1).setBackground(getResources().getDrawable((int) questionsUsed.get(1).get(0), null));
        btns.get(2).setBackground(getResources().getDrawable((int) questionsUsed.get(2).get(0), null));
        btns.get(3).setBackground(getResources().getDrawable((int) questionsUsed.get(3).get(0), null));

        //choose a correct answer, then get that image's string and set it as the question
        question.setText((int) questionsUsed.get(correctAnswer).get(1));

        //set the correct answer's onClickListener
        btns.get(correctAnswer).setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                correct(v);
            }
        });
    }

    /**
     * This method is used to save information about the game when we rotate the screen.
     * It overrides the super onSaveInstanceState method.
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("questionCounter" , questionCounter);
        outState.putInt("correctCounter" , correctCounter );
        outState.putInt("incorrectCounter" , incorrectCounter );
        if (questionsUsed.size()!=0) {
            outState.putInt("q1", (int) questionsUsed.get(0).get(2));
            outState.putInt("q2", (int) questionsUsed.get(1).get(2));
            outState.putInt("q3", (int) questionsUsed.get(2).get(2));
            outState.putInt("q4", (int) questionsUsed.get(3).get(2));
        }
        outState.putInt("qcorrect", correctAnswer);
        outState.putBoolean("firstIncorrect", firstIncorrect);
        outState.putInt("falseAnswerSignal", falseAnswerSignal);
        outState.putBoolean("btnNextVisibility", btnNext.getVisibility()==View.VISIBLE);
        outState.putInt("btnListenerOff", btnListenerOff);
    }

    /**
     *
     * @param v
     */
    public void correct(View v)
    {
        questionsUsed.remove(correctAnswer); // remove this question forever

        //move the other answers back into usable pool
        questionPool.add(questionsUsed.get(0));
        questionPool.add(questionsUsed.get(1));
        questionPool.add(questionsUsed.get(2));
        questionsUsed.remove(0);
        questionsUsed.remove(0);
        questionsUsed.remove(0);

        correctCounter++;
        questionCounter++;
        setAllBtnListenerOff();
        ((ImageButton) v).setImageResource(R.drawable.correct);//mark img as correct

        btnNext.setVisibility(View.VISIBLE);
        updateCounters();
    }

    /**
     *
     * @param v
     */
    public void incorrect(View v)
    {
        ((ImageButton) v).setImageResource(R.drawable.incorrect);// mark img as incorrect

        ((ImageButton) v).setOnClickListener(new View.OnClickListener() { //turn off click listener
            public void onClick(View v) {  }
        });
        btnListenerOff=(int)v.getTag();
        incorrectCounter++;

        if (firstIncorrect) // if this is the first incorrect
        {
            firstIncorrect = false;
            falseAnswerSignal = (int) v.getTag();
            Log.d("MYTYPE", " falseanswer ::::" + falseAnswerSignal);
        }
        else // if this is second incorrect
        {
            setAllBtnListenerOff();
            btnNext.setVisibility(View.VISIBLE);
            btns.get(correctAnswer).setImageResource(R.drawable.correct);//mark img as correct

            questionCounter++;
            //put all qs back into pool
            questionPool.add(questionsUsed.get(0));
            questionPool.add(questionsUsed.get(1));
            questionPool.add(questionsUsed.get(2));
            questionPool.add(questionsUsed.get(3));
            questionsUsed.remove(0);
            questionsUsed.remove(0);
            questionsUsed.remove(0);
            questionsUsed.remove(0);
        }
        updateCounters();

        Log.d("MYTYPE" ,  "questionsUsed size: " + questionsUsed.size());
        Log.d("MYTYPE" ,  "questionPool size: " + questionPool.size());
    }

    /**
     * This method updates the text that shows the counters for the correct/incorrect number of
     * questions and the number of questions answered at that point.
     * If the question counter reaches 10, it means that it is the end of the game and fires
     * the result activity. It then resets the counters and gets the next set of question for when
     * the user wants to play again.
     */
    public void updateCounters()
    {
        scoreText.setText(getString(R.string.scoreText1) + " " + correctCounter +  " " + getString(R.string.scoreText2) +incorrectCounter +  " " + getString(R.string.scoreText3));
        questionCounterText.setText(getString(R.string.counterText1) + " " + questionCounter +  " " + getString(R.string.counterText2));

        highscore=correctCounter;

        if (questionCounter == 10)
        {
            incorrectCounter =0;
            correctCounter=0;
            questionCounter=0;
            startActivityForResult(new Intent(this, ResultActivity.class), 9);

            nextQuestion(null);
            updateCounters();


        }
    }

    /**
     * This method fires the AboutActiviy.
     * @param v
     */
    public void aboutGameInfo(View v)
    {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("highscore", highscore);
        startActivity(intent);
    }

    /**
     * This method fires the SearchActivity.
     * @param v
     */
    public void hintSearch(View v)
    {
        TextView question = (TextView)findViewById(R.id.question);
        String q = question.getText().toString();
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, getString(R.string.searchPrefix) + " " + q);
        startActivity(intent);
    }

}
