package com.example.quizapp_m3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    TextView txtregistered;
    EditText etNewName,etNewEmail,etNewPassword; // for Sign up
    Button btnRegister;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etNewName = findViewById(R.id.etNewName);
        etNewEmail = findViewById(R.id.etNewEmail);
        etNewPassword = findViewById(R.id.etNewPassword);
        txtregistered =findViewById(R.id.txtregistered);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(Register.this,Quiz.class));
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etNewEmail.getText().toString().trim();
                String password = etNewPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    etNewEmail.setError("Email in Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etNewPassword.setError("Password in Required.");
                    return;
                }
                if(password.length()<6){
                    etNewPassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                // register The user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,Quiz.class));
                        }
                        else{
                            Toast.makeText(Register.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        txtregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                Toast.makeText(Register.this,"Login",Toast.LENGTH_LONG).show();
            }
        });
    }
}
