package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.phonebook.databinding.ActivityContactDetailBinding;
import com.example.phonebook.model.Contact;

public class ContactDetailActivity extends AppCompatActivity {

    ActivityContactDetailBinding binding;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | /*View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |*/ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        super.onCreate(savedInstanceState);
        binding = ActivityContactDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundle();
    }

    private void getBundle() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            Contact contact = (Contact) bundle.getSerializable("Contact");
            binding.tvName.setText(contact.getName());
            binding.tvNumber.setText(contact.getNumber());
        }
    }
}