package em.topquiz.org.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import em.topquiz.org.R;
import em.topquiz.org.model.Question;
import em.topquiz.org.model.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestionTv;
    private Button mAnswer1Btn, mAnswer2Btn, mAnswer3Btn, mAnswer4Btn;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mScore;
    private int mNumberOfQuestion;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";
    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate()");

        if(savedInstanceState != null){
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestion = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestion = 4;
        }


        mEnableTouchEvents = true; //valide ou invalide le fait que le user touche l'écran


        mQuestionBank = this.generateQuestion();    //inisliser la banq de questions

        //widget
        mQuestionTv = findViewById(R.id.activity_game_question_tv);
        mAnswer1Btn = findViewById(R.id.activity_game_answer1_btn);
        mAnswer2Btn = findViewById(R.id.activity_game_answer2_btn);
        mAnswer3Btn = findViewById(R.id.activity_game_answer3_btn);
        mAnswer4Btn = findViewById(R.id.activity_game_answer4_btn);

        //Use tag proprety to 'name' the button
        mAnswer1Btn.setTag(0);
        mAnswer2Btn.setTag(1);
        mAnswer3Btn.setTag(2);
        mAnswer4Btn.setTag(3);

        mAnswer1Btn.setOnClickListener(this);
        mAnswer2Btn.setOnClickListener(this);
        mAnswer3Btn.setOnClickListener(this);
        mAnswer4Btn.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override   //sauvegarde l'etat de l'appli (score et question)a un instant T
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestion);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {   //cette method est app qlq soit le button clické
        int responseIndex = (int) v.getTag();
        if(responseIndex == mCurrentQuestion.getAnswerIndex()){
            //good answer
            Toast.makeText(this, "Bonne réponse", Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else{
            //wrong answer
            Toast.makeText(this, "Mauvaise réponse", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false; //dès qu'une reponse est delectionné, l'écran est désactivé pdt 2 sec
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                //if is last question, ends the game else display next question
                if(--mNumberOfQuestion == 0){   //decremente avant de comparer
                    //end of game
                    endGame();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000);
    }

    @Override   //cette meth est app ds l'activity à chaque fois que le user touche l'ecran
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);    //on creer le builder

        //on construit la boite de dialogue
        builder.setTitle("Well done")
                .setMessage("your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //end of activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    public void displayQuestion(Question pQuestion){
        mQuestionTv.setText(pQuestion.getQuestion());
        mAnswer1Btn.setText(pQuestion.getChoiceList().get(0));
        mAnswer2Btn.setText(pQuestion.getChoiceList().get(1));
        mAnswer3Btn.setText(pQuestion.getChoiceList().get(2));
        mAnswer4Btn.setText(pQuestion.getChoiceList().get(3));
    }

    public QuestionBank generateQuestion (){

        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        Question question4 = new Question("How many do 7 * 7 ?",
                Arrays.asList("47", "49", "51", "42"),
                1);

        Question question5 = new Question("What is the capital of Romania?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6 = new Question("Who did the Mona Lisa paint?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("In which city is the composer Frédéric Chopin buried?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("What is the country top-level domain of Belgium?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);


        return new QuestionBank(Arrays.asList(question1, question2, question3, question4,
                    question5, question6, question7, question8, question9));
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("GameActivity::onDestroy()");
    }


}
