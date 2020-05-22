package com.assasin.varia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.assasin.varia.data.AnswerListAsyncResp;
import com.assasin.varia.data.QuestionBank;
import com.assasin.varia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView;
    private TextView questionCounterTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private int questionNumber = 0;
    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        questionCounterTextView = findViewById(R.id.counter);
        questionTextView = findViewById(R.id.question_view);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResp()

        {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                questionTextView.setText(questionArrayList.get(questionNumber).getAnswer());
                questionCounterTextView.setText(questionNumber + " out of " + questionArrayList.size());

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.prev_button:
                if (questionNumber != 0) {
                    questionNumber = (questionNumber - 1) % questionList.size();
                    updateQuestion();
                }
                break;

            case R.id.next_button:
                questionNumber = (questionNumber + 1) % questionList.size();
                updateQuestion();
                break;

            case R.id.true_button:
                checkAnswer(true);
                updateQuestion();
                break;

            case R.id.false_button:
                checkAnswer(false);
                updateQuestion();
                break;

        }
    }

    private void checkAnswer(boolean userChoice) {
        boolean answerIsTrue = questionList.get(questionNumber).isAnswerTrue();

        if (userChoice == answerIsTrue)
           {
               Toast.makeText(MainActivity.this, R.string.correct_answer, Toast.LENGTH_SHORT).show();
               fadeAnim();
           }
        else
            {
                Toast.makeText(MainActivity.this, R.string.wrong_answer, Toast.LENGTH_SHORT).show();
                shakeAnim();
            }

    }

    private void shakeAnim(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);

        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackground(getDrawable(R.drawable.card_view_bg_red_anim));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackground(getDrawable(R.drawable.card_view_bg));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeAnim()
    {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackground(getDrawable(R.drawable.card_view_bg_green_anim));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackground(getDrawable(R.drawable.card_view_bg));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void updateQuestion() {

        String question = questionList.get(questionNumber).getAnswer();
        questionTextView.setText(question);
        questionCounterTextView.setText(questionNumber + " out of " + questionList.size());

    }
}
