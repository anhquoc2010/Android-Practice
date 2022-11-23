package com.example.loginlisttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginlisttest.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.progressBarLogin.setVisibility(ProgressBar.GONE);

        binding.textViewRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        binding.buttonLogin.setOnClickListener(v -> {
            String textEmail = binding.editTextUserNameLogin.getText().toString();
            String textPassword = binding.editTextPSWDLogin.getText().toString();

            //check if all the data are present
            if (TextUtils.isEmpty(textEmail)) {
                binding.editTextUserNameLogin.setError("Please enter your email");
                binding.editTextUserNameLogin.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                binding.editTextUserNameLogin.setError("Invalid email");
                binding.editTextUserNameLogin.requestFocus();
            } else if (TextUtils.isEmpty(textPassword)) {
                binding.editTextPSWDLogin.setError("Please enter your password");
                binding.editTextPSWDLogin.requestFocus();
            } else {
                binding.progressBarLogin.setVisibility(android.view.View.VISIBLE);
                loginUser(textEmail, textPassword);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.progressBarLogin.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void loginUser(String textEmail, String textPassword) {
        auth.signInWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ListShowActivity.class));
                    finish();
                } else {
                    Log.w("TAG", "loginWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
                binding.progressBarLogin.setVisibility(android.view.View.GONE);
            }
        });
    }
}