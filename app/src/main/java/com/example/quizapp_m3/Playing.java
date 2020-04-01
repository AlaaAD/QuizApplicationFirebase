package com.example.quizapp_m3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp_m3.Model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Playing extends AppCompatActivity {
        TextView questionTxt,timerTxt;
        Button btnOption1,btnOption2,btnOption3,btnOption4;

        int total=0;
        int correct=0,wrong=0;
        DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);


        questionTxt = findViewById(R.id.questionTxt);
        timerTxt = findViewById(R.id.timerTxt);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);
        updateQuestion();
        reverseTimer(120,timerTxt);

    }

    private void updateQuestion() {
        total++;
        if(total >5){
                // Open Score Activity
            Intent intent = new Intent(Playing.this, Score.class);
            intent.putExtra("total", String.valueOf(total));
            intent.putExtra("correct", String.valueOf(correct));
            intent.putExtra("incorrect", String.valueOf(wrong));
            startActivity(intent);
        }
        else{
            // get Question from Firebase Database
           reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
           reference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   final Question question = dataSnapshot.getValue(Question.class);
                   questionTxt.setText(question.getQuestion());
                   btnOption1.setText(question.getOption1());
                   btnOption2.setText(question.getOption2());
                   btnOption3.setText(question.getOption3());
                   btnOption4.setText(question.getOption4());

                //Answer is correct or wrong
                btnOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(btnOption1.getText().toString().equals(question.getAnswer())){
                            btnOption1.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    correct++;
                                    btnOption1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                    updateQuestion();
                                }
                            },1500);
                        }
                        else{
                            // answer if wrong ... we will find the correct answer and make it green
                            wrong ++;
                            btnOption1.setBackgroundColor(Color.RED);
                            if(btnOption2.getText().toString().equals(question.getAnswer())){
                                btnOption2.setBackgroundColor(Color.GREEN);
                            }
                            else if(btnOption3.getText().toString().equals(question.getAnswer())) {
                                btnOption3.setBackgroundColor(Color.GREEN);
                            }
                            else if(btnOption4.getText().toString().equals(question.getAnswer())) {
                                btnOption4.setBackgroundColor(Color.GREEN);
                            }
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    btnOption1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                    btnOption2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                    btnOption3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                    btnOption4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                    updateQuestion();
                                }
                            },1500);
                        }
                    }
                });

                btnOption2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(btnOption2.getText().toString().equals(question.getAnswer())){
                               btnOption2.setBackgroundColor(Color.GREEN);
                               Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       correct++;
                                       btnOption2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       updateQuestion();
                                   }
                               },1500);
                           }
                           else{
                               // answer if wrong ... we will find the correct answer and make it green
                               wrong ++;
                               btnOption2.setBackgroundColor(Color.RED);
                               if(btnOption1.getText().toString().equals(question.getAnswer())){
                                   btnOption1.setBackgroundColor(Color.GREEN);
                               }
                               else if(btnOption3.getText().toString().equals(question.getAnswer())) {
                                   btnOption3.setBackgroundColor(Color.GREEN);
                               }
                               else if(btnOption4.getText().toString().equals(question.getAnswer())) {
                                   btnOption4.setBackgroundColor(Color.GREEN);
                               }
                               Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       btnOption1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       btnOption2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       btnOption3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       btnOption4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       updateQuestion();
                                   }
                               },1500);
                           }
                       }
                   });
                btnOption3.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(btnOption3.getText().toString().equals(question.getAnswer())){
                               btnOption3.setBackgroundColor(Color.GREEN);
                               Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       correct++;
                                       btnOption3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       updateQuestion();
                                   }
                               },1500);
                           }
                           else{
                               // answer if wrong ... we will find the correct answer and make it green
                               wrong ++;
                               btnOption3.setBackgroundColor(Color.RED);
                               if(btnOption2.getText().toString().equals(question.getAnswer())){
                                   btnOption2.setBackgroundColor(Color.GREEN);
                               }
                               else if(btnOption1.getText().toString().equals(question.getAnswer())) {
                                   btnOption1.setBackgroundColor(Color.GREEN);
                               }
                               else if(btnOption4.getText().toString().equals(question.getAnswer())) {
                                   btnOption4.setBackgroundColor(Color.GREEN);
                               }
                               Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       btnOption1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       btnOption2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       btnOption3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       btnOption4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       updateQuestion();
                                   }
                               },1500);
                           }
                       }
                   });
                btnOption4.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(btnOption4.getText().toString().equals(question.getAnswer())){
                               btnOption4.setBackgroundColor(Color.GREEN);
                               Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       correct++;
                                       btnOption4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       updateQuestion();
                                   }
                               },1500);
                           }
                           else{
                               // answer if wrong ... we will find the correct answer and make it green
                               wrong ++;
                               btnOption4.setBackgroundColor(Color.RED);
                               if(btnOption2.getText().toString().equals(question.getAnswer())){
                                   btnOption2.setBackgroundColor(Color.GREEN);
                               }
                               else if(btnOption3.getText().toString().equals(question.getAnswer())) {
                                   btnOption3.setBackgroundColor(Color.GREEN);
                               }
                               else if(btnOption1.getText().toString().equals(question.getAnswer())) {
                                   btnOption1.setBackgroundColor(Color.GREEN);
                               }
                               Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       btnOption1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       btnOption2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       btnOption3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       btnOption4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       updateQuestion();
                                   }
                               },1500);
                           }
                       }
                   });
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
        }

    }
    public void reverseTimer(int Seconds,final TextView tv) {
        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d", minutes)
                        + " : " + String.format("%02d", seconds));
            }


            public void onFinish() {
                tv.setText("Completed");
                Intent intent = new Intent(Playing.this, Score.class);
                intent.putExtra("total", total);
                intent.putExtra("correct", correct);
                intent.putExtra("incorrect", wrong);
                startActivity(intent);
            }


        }.start();
    }
}
