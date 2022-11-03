package com.example.sqlite;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * class hiển thị thông tin Danh mục và Spinner
 * và hiển thị thông tin máy tính vào ListView
 * đồng thời cho phép thao tác với máy tính
 */

public class InsertComputerActivity extends AppCompatActivity {

    SQLiteDatabase database = null;
    List<InforData> listComputer = null;
    List<InforData> listCategory = null;
    InforData categoryData = null;
    MySimpleArrayAdapter adapter = null;
    ActivityResultLauncher<Intent> startForResult;
    public static final int SEND_DATA_FROM_COMPUTER_ACTIVITY = 3;
    InforData dataClick = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_computer);

        startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == SEND_DATA_FROM_COMPUTER_ACTIVITY) {
                        Intent data = result.getData();
                        Bundle bundle = data.getBundleExtra("DATA_COMPUTER");
                        String f3 = bundle.getString("price");
                        String f2 = bundle.getString("name");
                        String f1 = dataClick.getField1().toString();
                        ContentValues content = new ContentValues();
                        content.put("name", f2);
                        content.put("price", f3);
                        if (database != null) {
                            int n = database.update("tblComputer", content, "id=?", new String[]{f1});
                            if (n > 0) {
                                Toast.makeText(InsertComputerActivity.this, "update ok ok ok ", Toast.LENGTH_LONG).show();
                                dataClick.setField2(f2);
                                dataClick.setField3(f3);
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        Spinner pinner = (Spinner) findViewById(R.id.spinner1);
        listCategory = new ArrayList<InforData>();
        InforData d1 = new InforData();
        d1.setField1("_");
        d1.setField2("Show All");
        d1.setField3("_");
        listCategory.add(d1);
        //Lệnh xử lý đưa dữ liệu là Danh mục và Spinner
        database = openOrCreateDatabase("mydata.db", MODE_PRIVATE, null);
        if (database != null) {

            Cursor cursor = database.query("tblCategory", null, null, null, null, null, null);
            cursor.moveToFirst();
            int index = 1;
            while (cursor.isAfterLast() == false) {
                InforData d = new InforData();
                d.setField1(index);
                d.setField2(cursor.getInt(0));
                d.setField3(cursor.getString(1));
                listCategory.add(d);
                index++;
                cursor.moveToNext();
            }
            cursor.close();
        }
        adapter = new MySimpleArrayAdapter(InsertComputerActivity.this, R.layout.layout_show_list, listCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pinner.setAdapter(adapter);

        //Xử lý sự kiện chọn trong Spinner
        //chọn danh mục nào thì hiển thị toàn bộ máy tính của danh mục đó mà thôi
        //Nếu chọn All thì hiển thị toàn bộ không phân hiệt danh mục
        pinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 == 0) {
                    //Hiển thị mọi máy tính  trong CSDL
                    categoryData = null;
                    loadAlllistComputer();
                } else {
                    //Hiển thị sách theo máy tính chọn trong Spinner
                    categoryData = listCategory.get(arg2);
                    loadListComputerByCategory(categoryData.getField1().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                categoryData = null;
            }
        });

        //Lệnh xử lý thêm mới một sản phẩm danh mục đang chọn
        Button btnInsertComputer = (Button) findViewById(R.id.buttonInsertComputerNe);
        btnInsertComputer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (categoryData == null) {
                    Toast.makeText(InsertComputerActivity.this, "Please choose an category to insert", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText txtTitle = (EditText) findViewById(R.id.editTextTitle);
                EditText txtPrice = (EditText) findViewById(R.id.editTextPrice);
                ContentValues values = new ContentValues();
                values.put("name", txtTitle.getText().toString());
                values.put("price", txtPrice.getText().toString());
                values.put("categoryid", categoryData.getField2().toString());
                long bId = database.insert("tblComputer", null, values);
                if (bId > 0) {
                    Toast.makeText(InsertComputerActivity.this, "Insert Computer OK", Toast.LENGTH_LONG).show();
                    loadListComputerByCategory(categoryData.getField2().toString());
                } else {
                    Toast.makeText(InsertComputerActivity.this, "Insert Computer Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
     * Hàm hiển thị mọi máy tính trong CSDL
     */
    public void loadAlllistComputer() {
        Cursor cur = database.query("tblComputer", null, null, null, null, null, null);
        cur.moveToFirst();
        listComputer = new ArrayList<InforData>();
        while (cur.isAfterLast() == false) {
            InforData d = new InforData();
            d.setField1(cur.getInt(0));
            d.setField2(cur.getString(1));
            d.setField3(cur.getInt(2));
            listComputer.add(d);
            cur.moveToNext();
        }
        cur.close();
        adapter = new MySimpleArrayAdapter(InsertComputerActivity.this, R.layout.layout_show_list, listComputer);
        ListView lv = (ListView) findViewById(R.id.listViewComputer);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(InsertComputerActivity.this, "View -->" + listComputer.get(arg2).toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(InsertComputerActivity.this, UpdateComputerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("KEY", 1);
                bundle.putString("getField1C", listComputer.get(arg2).getField1().toString());
                bundle.putString("getField2C", listComputer.get(arg2).getField2().toString());
                bundle.putString("getField3C", listComputer.get(arg2).getField3().toString());
                intent.putExtra("DATAC", bundle);
                dataClick = listComputer.get(arg2);
                startForResult.launch(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                // TODO Auto-generated method stub
                final InforData data = listComputer.get(arg2);
                final int pos = arg2;
                Toast.makeText(InsertComputerActivity.this, "Edit-->" + data.toString(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder b = new AlertDialog.Builder(InsertComputerActivity.this);
                b.setTitle("Remove");
                b.setMessage("Xóa [" + data.getField1() + " - " + data.getField2() + " - " + data.getField3() + "] hả?");
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        int n = database.delete("tblComputer", "id=?", new String[]{data.getField1().toString()});
                        if (n > 0) {
                            Toast.makeText(InsertComputerActivity.this, "Remove ok", Toast.LENGTH_LONG).show();
                            listComputer.remove(pos);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(InsertComputerActivity.this, "Remove not ok", Toast.LENGTH_LONG).show();
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
                return false;
            }
        });
    }

    /**
     * hàm hiển thị máy tính theo danh mục
     *
     * @param categoryid
     */
    public void loadListComputerByCategory(String categoryid) {
        Cursor cur = database.query("tblComputer", null, "categoryid=?", new String[]{categoryid}, null, null, null);
        cur.moveToFirst();
        listComputer = new ArrayList<InforData>();
        while (cur.isAfterLast() == false) {
            InforData d = new InforData();
            d.setField1(cur.getInt(0));
            d.setField2(cur.getString(1));
            d.setField3(cur.getInt(2));
            listComputer.add(d);
            cur.moveToNext();
        }
        cur.close();
        adapter = new MySimpleArrayAdapter(InsertComputerActivity.this, R.layout.layout_show_list, listComputer);
        ListView lv = (ListView) findViewById(R.id.listViewComputer);
        lv.setAdapter(adapter);
    }
}