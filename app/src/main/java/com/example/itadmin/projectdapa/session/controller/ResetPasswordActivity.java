package com.example.itadmin.projectdapa.session.controller;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.itadmin.projectdapa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button resetBtn, backBtn;
    private ProgressBar progressBar;
    private EditText emailText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailText = (EditText) findViewById(R.id.email);
        resetBtn = (Button) findViewById(R.id.btn_reset_password);
        backBtn = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        mAuth = FirebaseAuth.getInstance();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailText.setError("Please Enter an email!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this, "Reset email instructions sent to " + email, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, "Failed to send email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
