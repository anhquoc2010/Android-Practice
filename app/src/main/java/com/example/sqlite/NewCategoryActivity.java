package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
        final Button btnInsert = (Button) findViewById(R.id.buttonInsert);
        final EditText txtName = (EditText) findViewById(R.id.editTextName);
        final Intent intent = getIntent();
        btnInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("name", txtName.getText().toString());
                intent.putExtra("DATA_CATEGORY", bundle);
                setResult(MainActivity.SEND_DATA_FROM_CATEGORY_ACTIVITY, intent);
                NewCategoryActivity.this.finish();
            }
        });
        final Button btnClear = (Button) findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                txtName.setText("");
                txtName.requestFocus();
            }
        });

        Bundle bundle = intent.getBundleExtra("DATA");
        if (bundle != null && bundle.getInt("KEY") == 1) {
            String f3 = bundle.getString("getField3");
            txtName.setText(f3);
            btnInsert.setText("Update");
            this.setTitle("View Detail");
			/*TableRow row=(TableRow) findViewById(R.id.tableRow3);
			row.removeViewAt(0);
			row.setGravity(Gravity.RIGHT);*/
        }
    }
}