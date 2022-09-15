package com.example.w2_e1_ui_profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressBar progressBar;
    private TextView textViewSignupLink;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        findViews();

        buttonLogin.setOnClickListener(v -> {
            String textEmail = editTextEmail.getText().toString();
            String textPassword = editTextPassword.getText().toString();

            //check if all the data are present
            if (TextUtils.isEmpty(textEmail)) {
                editTextEmail.setError("Please enter your email");
                editTextEmail.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                editTextEmail.setError("Invalid email");
                editTextEmail.requestFocus();
            } else if (TextUtils.isEmpty(textPassword)) {
                editTextPassword.setError("Please enter your password");
                editTextPassword.requestFocus();
            } else {
                progressBar.setVisibility(android.view.View.VISIBLE);
                loginUser(textEmail, textPassword);
            }
        });

        textViewSignupLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }

    private void loginUser(String textEmail, String textPassword) {
        auth.signInWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                } else {
                    Log.w("TAG", "loginWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(android.view.View.GONE);
            }
        });
    }

    private void findViews() {
        buttonLogin = findViewById(R.id.button_login);
        editTextEmail = findViewById(R.id.editTextUserName_login);
        editTextPassword = findViewById(R.id.editTextPSWD_login);
        progressBar = findViewById(R.id.progressBarLogin);
        textViewSignupLink = findViewById(R.id.textView_register_link);
    }
}