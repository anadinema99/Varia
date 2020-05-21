package com.assasin.varia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.assasin.varia.data.AnswerListAsyncResp;
import com.assasin.varia.data.QuestionBank;
import com.assasin.varia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Question> questionList = new QuestionBank().getQuestions(new AnswerListAsyncResp() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                Log.d("Main", "processFinished: " + questionArrayList);

            }
        });


    }
}
