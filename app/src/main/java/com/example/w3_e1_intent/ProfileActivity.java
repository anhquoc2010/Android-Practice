package com.example.w3_e1_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.w3_e1_intent.custom.SinhVien;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton imageButtonBack;
    private TextView textViewEmail;
    private TextView textViewHoTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        transparentStatus();
        findViews();

        SinhVien sinhVien = (SinhVien) getIntent().getSerializableExtra("sinhVienNe");
        if (sinhVien != null) {
            textViewHoTen.setText(sinhVien.getHoTen().toString());
            textViewEmail.setText(sinhVien.getEmail().toString());
        }

        imageButtonBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void findViews() {
        imageButtonBack = findViewById(R.id.ib_back);
        textViewEmail = findViewById(R.id.tv_email);
        textViewHoTen = findViewById(R.id.tv_name);
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