package com.example.w2_e1_ui_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton imageButtonBack;
    private ProgressBar progressBar;
    private TextView textViewName;
    private TextView textViewEmail;

    private GridView gridView_top_tokens;
    private GridView gridView_top_networks;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();

        transparentStatus();
        findViews();

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(user);
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }

        imageButtonBack.setOnClickListener(v -> {
            signOut();
            finish();
        });
    }

    private void showUserProfile(FirebaseUser user) {
        FirebaseUserMetadata metadata = user.getMetadata();

        long creationTimestamp = metadata.getCreationTimestamp();

        String datePattern = "E, dd/MM/yyyy hh:mm a z";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String register = simpleDateFormat.format(new Date(creationTimestamp));

        String registerDate = getResources().getString(R.string.register_date, register);
        textViewEmail.setText(registerDate);

        String name = user.getEmail();
        textViewName.setText(name);

        progressBar.setVisibility(View.GONE);
    }

    private void signOut() {
        auth.signOut();
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        imageButtonBack = findViewById(R.id.ib_back);
        textViewName = findViewById(R.id.tv_name);
        textViewEmail = findViewById(R.id.tv_email);
        progressBar = findViewById(R.id.progressBarProfile);
    }

    private void transparentStatus() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(int i, boolean b) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        if (b) {
            layoutParams.flags |= i;
        } else {
            layoutParams.flags &= ~i;
        }
        getWindow().setAttributes(layoutParams);
    }
}