package com.jay.emergencycontact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private ArrayList<Contact> contacts;
    private Context context;
    private int resource;

    public ContactAdapter(@NonNull Context context, int resource,ArrayList<Contact> contacts) {
        super(context, resource,contacts);
        this.contacts = contacts;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
        }
        TextView name = convertView.findViewById(R.id.contact_row_name);
        ImageButton delete = convertView.findViewById(R.id.delete_row);

        final Contact currentContact = getItem(position);
        final ContactsHelper helper = ContactsHelper.getInstance(context);

        name.setText(currentContact.getName());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewContact = new Intent(context,ViewContact.class);
                viewContact.putExtra(ViewContact.ROW_ID_KEY,currentContact.getRowId());
                context.startActivity(viewContact);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.deleteContact(currentContact.getRowId());
                contacts.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
