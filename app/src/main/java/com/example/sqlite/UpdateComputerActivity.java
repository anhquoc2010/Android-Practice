package com.example.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateComputerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_computer);
        final Button btnInsert = (Button) findViewById(R.id.buttonInsert);
        final EditText txtName = (EditText) findViewById(R.id.editTextName);
        final EditText txtPrice = (EditText) findViewById(R.id.editTextPrice);
        final Intent intent = getIntent();
        btnInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("name", txtName.getText().toString());
                bundle.putString("price", txtPrice.getText().toString());
                intent.putExtra("DATA_COMPUTER", bundle);
                setResult(InsertComputerActivity.SEND_DATA_FROM_COMPUTER_ACTIVITY, intent);
                UpdateComputerActivity.this.finish();
            }
        });
        final Button btnClear = (Button) findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtName.setText("");
                txtPrice.setText("");
                txtName.requestFocus();
            }
        });

        Bundle bundle = intent.getBundleExtra("DATAC");
        if (bundle != null && bundle.getInt("KEY") == 1) {
            String f3 = bundle.getString("getField3C");
            String f2 = bundle.getString("getField2C");
            txtName.setText(f2);
            txtPrice.setText(String.valueOf(f3));
            btnInsert.setText("Update");
            this.setTitle("View Detail");
			/*TableRow row=(TableRow) findViewById(R.id.tableRow3);
			row.removeViewAt(0);
			row.setGravity(Gravity.RIGHT);*/
        }
    }
}