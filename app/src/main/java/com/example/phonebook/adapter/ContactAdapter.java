package com.example.phonebook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.ContactDetailActivity;
import com.example.phonebook.R;
import com.example.phonebook.model.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Contact> contactsList;

    public ContactAdapter(Context context, ArrayList<Contact> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Gán View
        View view = LayoutInflater.from(context).inflate(R.layout.contact_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        // Gán dữ liệu
        Contact contact = contactsList.get(position);

        holder.tvName.setText(contact.getName());
        holder.tvNumber.setText(contact.getNumber());

        holder.layoutContactItem.setOnClickListener(v -> {
            onClickDetailContact(contact);
        });
    }

    private void onClickDetailContact(Contact contact) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Contact", contact);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.fade_out);
    }

    @Override
    public int getItemCount() {
        if (contactsList != null) {
            return contactsList.size();
        } else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvNumber;
        ConstraintLayout layoutContactItem;

        ImageView ivMark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);

            layoutContactItem = itemView.findViewById(R.id.cl_contact_layout);
        }
    }
}
