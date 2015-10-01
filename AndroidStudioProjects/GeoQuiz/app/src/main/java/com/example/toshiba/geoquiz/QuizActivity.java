package com.example.toshiba.geoquiz;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;


public class QuizActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String INDEX = "index";
    private static final String KEY = "is_a_cheater";
    private static final String TAG = "QuizActivity";

    private Button mTrueButton;
    private Button mFalseButton;
   // private Button mNextButton;
    private LinearLayout mMainLinLayout;
    private TextView mQuestionTextView;
    //private Button mPreviousButton;
    private ImageButton mNextImageButton;
    private ImageButton mPrevImageButton;
    private Button mCheatButton;


    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId;

        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        }else{
            if (userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            }else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY);
        }


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mMainLinLayout = (LinearLayout) findViewById(R.id.main_linear_layout);
        mMainLinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        //mNextButton = (Button) findViewById(R.id.next_button);
        //mNextButton.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                //updateQuestion();

          //  }
        //});
        //mPreviousButton = (Button) findViewById(R.id.previous_button);
        //mPreviousButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
          //  public void onClick(View v) {
            //    if (mCurrentIndex == 0) mCurrentIndex = 5;
              //  mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                //updateQuestion();
            //}
        //});

        mNextImageButton = (ImageButton) findViewById(R.id.next_I_button);
        mNextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
               // startActivity(i);
            }
        });

        mPrevImageButton = (ImageButton) findViewById(R.id.previous_I_button);
        mPrevImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) mCurrentIndex = 5;
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY, mIsCheater);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
