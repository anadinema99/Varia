package com.assasin.varia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.assasin.varia.controller.AppController;
import com.assasin.varia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.assasin.varia.controller.AppController.TAG;

public class QuestionBank {

    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/anadinema99/vario/master/VarioApp.json";

    public List<Question> getQuestions(final AnswerListAsyncResp callback) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                (JSONArray) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length(); i++)
                {
                    try {

                        Question question = new Question();

                        question.setAnswer(response.getJSONArray(i).get(0).toString());
                        question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

                        questionArrayList.add(question);

//                        Log.d("Trying", "onResponse: " + question.getAnswer());

//                        Log.d("JSON Stuff", "onResponse: " + response.getJSONArray(i).get(0));
//                        Log.d("JSON2 Stuff", "onResponse: " + response.getJSONArray(i).getBoolean(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (null != callback)
                    callback.processFinished(questionArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }

}
