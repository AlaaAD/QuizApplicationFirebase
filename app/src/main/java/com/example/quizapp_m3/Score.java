package com.example.quizapp_m3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class Score extends AppCompatActivity {

    TextView correctTxt,wrongTxt;
    Button btnlogOut,btnReplay;
    DonutProgress donut_progress;
    String total,correct,incorrect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        correctTxt = findViewById(R.id.correctTxt);
        wrongTxt = findViewById(R.id.wrongTxt);

        btnlogOut = findViewById(R.id.btnlogOut);
        btnReplay = findViewById(R.id.btnReplay);

        donut_progress = (DonutProgress) findViewById(R.id.donut_progress);



        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this,Playing.class);
                startActivity(intent);
                Toast.makeText(Score.this,"Replay",Toast.LENGTH_SHORT).show();
            }
        });

        btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Score.this,Login.class));
                finish();
                Toast.makeText(Score.this,"Log out",Toast.LENGTH_SHORT).show();
            }
        });




// recuperer les donnée de l'activity precedente et l'afficher sur l'activity actuel
        Intent intent = getIntent();
         total= intent.getStringExtra("total");
         correct = intent.getStringExtra("correct");
         incorrect = intent.getStringExtra("incorrect");



        correctTxt.setText(correct);
        wrongTxt.setText(incorrect);

        //chart
        donut_progress.setDonut_progress(String.valueOf(getResultat()));

    }

    private  int getResultat() {
        Double correctS= Double.valueOf(Integer.parseInt(correct));
       Double totalS = Double.valueOf(Integer.parseInt(total)-1);

       int score = (int) (correctS/totalS*100);
        FirebaseDatabase.getInstance().getReference("Current Location").child("score").push().setValue(score+" %");
        FirebaseDatabase.getInstance().getReference("Current Location").child("Time").push().setValue(String.format("%1$tY-%<tm-%<td %<tR", Calendar.getInstance())
        );
       return score;

    }


}
