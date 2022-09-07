package com.example.w2e1bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText height;
    EditText weight;
    TextView result;
    TextView nx;
    TextView kq;

    @SuppressLint({"DefaultLocale", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        result = findViewById(R.id.result);
        nx = findViewById(R.id.nhan_xet);
        kq = findViewById(R.id.ket_qua);

        btn.setOnClickListener(view -> {
            double h;
            double w;
            double res;

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(btn.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

            if (height.getText().toString().trim().isEmpty() || weight.getText().toString().trim().isEmpty() || (Double.parseDouble(height.getText().toString()) * Double.parseDouble(weight.getText().toString()) == 0.0)) {
                height.setText("");
                weight.setText("");
                result.setText(R.string.bmi);
                nx.setText(R.string.nh_n_x_t);
                result.setCompoundDrawablesWithIntrinsicBounds(R.drawable.binhthuong, 0, 0, 0);
                result.setTextColor(getResources().getColor(R.color.colorAccent));
                nx.setTextColor(getResources().getColor(R.color.colorAccent));
                kq.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                h = Double.parseDouble((height.getText()).toString()) / 100;
                w = Double.parseDouble((weight.getText()).toString());
                res = w / (h * h);
                result.setText(String.format("%.2f", res));
                if (res < 18) {
                    nx.setText(R.string.nguoi_gay);
                    result.setCompoundDrawablesWithIntrinsicBounds(R.drawable.gay, 0, 0, 0);
                    result.setTextColor(getResources().getColor(R.color.colorGay));
                    nx.setTextColor(getResources().getColor(R.color.colorGay));
                    kq.setTextColor(getResources().getColor(R.color.colorGay));
                } else if (res < 24.9) {
                    nx.setText(R.string.binh_thuong);
                    result.setCompoundDrawablesWithIntrinsicBounds(R.drawable.binhthuong, 0, 0, 0);
                    result.setTextColor(getResources().getColor(R.color.colorAccent));
                    nx.setTextColor(getResources().getColor(R.color.colorAccent));
                    kq.setTextColor(getResources().getColor(R.color.colorAccent));
                } else if (res < 29.9) {
                    nx.setText(R.string.beo_phi_1);
                    result.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beo1, 0, 0, 0);
                    result.setTextColor(getResources().getColor(R.color.colorBeo1));
                    nx.setTextColor(getResources().getColor(R.color.colorBeo1));
                    kq.setTextColor(getResources().getColor(R.color.colorBeo1));
                } else if (res < 34.9) {
                    nx.setText(R.string.beo_phi_2);
                    result.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beo2, 0, 0, 0);
                    result.setTextColor(getResources().getColor(R.color.colorBeo2));
                    nx.setTextColor(getResources().getColor(R.color.colorBeo2));
                    kq.setTextColor(getResources().getColor(R.color.colorBeo2));
                } else {
                    nx.setText(R.string.beo_phi_3);
                    result.setCompoundDrawablesWithIntrinsicBounds(R.drawable.beo3, 0, 0, 0);
                    result.setTextColor(getResources().getColor(R.color.colorBeo3));
                    nx.setTextColor(getResources().getColor(R.color.colorBeo3));
                    kq.setTextColor(getResources().getColor(R.color.colorBeo3));
                }
            }
        });
    }
}