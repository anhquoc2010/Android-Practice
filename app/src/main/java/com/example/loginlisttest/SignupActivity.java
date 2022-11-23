package com.example.loginlisttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private Button buttonSignup;
    private CheckBox checkBoxAgree;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private ProgressBar progressBar;
    private TextView textViewLoginLink;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViews();

        buttonSignup.setOnClickListener(v -> {
            String textEmail = editTextEmail.getText().toString();
            String textPassword = editTextPassword.getText().toString();
            String textConfirmPassword = editTextConfirmPassword.getText().toString();

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
            } else if (TextUtils.isEmpty(textConfirmPassword)) {
                editTextConfirmPassword.setError("Please confirm your password");
                editTextConfirmPassword.requestFocus();
            } else if (!textPassword.equals(textConfirmPassword)) {
                editTextConfirmPassword.setError("Passwords do not match");
                editTextConfirmPassword.requestFocus();
            } else if (textPassword.length() < 6) {
                editTextPassword.setError("Password must be at least 6 characters");
                editTextPassword.requestFocus();
            } else if (!checkBoxAgree.isChecked()) {
                checkBoxAgree.setError("Please agree to the terms and conditions");
                checkBoxAgree.requestFocus();
            } else {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                registerUser(textEmail, textPassword);
            }
        });

        textViewLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });
    }

    private void registerUser(String textEmail, String textPassword) {
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                Log.d("TAG", "createUserWithEmail:success");
                                Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void findViews() {
        buttonSignup = findViewById(R.id.button_signup);
        checkBoxAgree = findViewById(R.id.checkBoxAgree);
        editTextEmail = findViewById(R.id.editTextTextPersonName_signup);
        editTextPassword = findViewById(R.id.editTextPass_signup);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPass);
        progressBar = findViewById(R.id.progressBarSignup);
        textViewLoginLink = findViewById(R.id.textView_login_link);
    }
}