package com.example.sqlite;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowListActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> startForResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == MainActivity.SEND_DATA_FROM_CATEGORY_ACTIVITY) {
                        Intent data = result.getData();
                        Bundle bundle = data.getBundleExtra("DATA_CATEGORY");
                        String f3 = bundle.getString("name");
                        String f2 = dataClick.getField2().toString();
                        ContentValues content = new ContentValues();
                        content.put("name", f3);
                        if (database != null) {
                            int n = database.update("tblCategory", content, "id=?", new String[]{f2});
                            if (n > 0) {
                                Toast.makeText(ShowListActivity.this, "update ok ok ok ", Toast.LENGTH_LONG).show();
                                dataClick.setField3(f3);
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        updateUI();
        Button btn = (Button) findViewById(R.id.buttonBack);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowListActivity.this.finish();
            }
        });
    }

    List<InforData> list = new ArrayList<InforData>();
    InforData dataClick = null;
    SQLiteDatabase database = null;
    MySimpleArrayAdapter adapter = null;

    public void updateUI() {
        database = openOrCreateDatabase("mydata.db", MODE_PRIVATE, null);
        if (database != null) {

            Cursor cursor = database.query("tblCategory", null, null, null, null, null, null);
            InforData header = new InforData();
            header.setField1("STT");
            header.setField2("Mã danh mục");
            header.setField3("Tên danh mục");
            list.add(header);
            cursor.moveToFirst();
            int index = 1;
            while (!cursor.isAfterLast()) {
                InforData data = new InforData();
                data.setField1(index);
                data.setField2(cursor.getInt(0));
                data.setField3(cursor.getString(1));
                list.add(data);
                index++;
                cursor.moveToNext();
            }
            cursor.close();
            adapter = new MySimpleArrayAdapter(ShowListActivity.this, R.layout.layout_show_list, list);
            final ListView lv = (ListView) findViewById(R.id.listViewShowData);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    Toast.makeText(ShowListActivity.this, "View -->" + list.get(arg2).toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ShowListActivity.this, NewCategoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("KEY", 1);
                    bundle.putString("getField1", list.get(arg2).getField1().toString());
                    bundle.putString("getField2", list.get(arg2).getField2().toString());
                    bundle.putString("getField3", list.get(arg2).getField3().toString());
                    intent.putExtra("DATA", bundle);
                    dataClick = list.get(arg2);
                    startForResult.launch(intent);
                }
            });
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    final InforData data = list.get(arg2);
                    final int pos = arg2;
                    Toast.makeText(ShowListActivity.this, "Edit-->" + data.toString(), Toast.LENGTH_LONG).show();
                    AlertDialog.Builder b = new AlertDialog.Builder(ShowListActivity.this);
                    b.setTitle("Remove");
                    b.setMessage("Xóa [" + data.getField2() + " - " + data.getField3() + "] hả?");
                    b.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            int n = database.delete("tblCategory", "id=?", new String[]{data.getField2().toString()});
                            if (n > 0) {
                                Toast.makeText(ShowListActivity.this, "Remove ok", Toast.LENGTH_LONG).show();
                                list.remove(pos);
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(ShowListActivity.this, "Remove not ok", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.cancel();
                        }
                    });
                    b.show();
                    return true;
                }
            });
        }
    }
}