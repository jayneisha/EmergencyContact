package com.jay.emergencycontact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton add = findViewById(R.id.add_contact);
        ListView contacts = findViewById(R.id.contacts);

        ContactsHelper helper = ContactsHelper.getInstance(MainActivity.this);
        ArrayList<Contact> contactsList = helper.getAllContacts();

        ContactAdapter adapter = new ContactAdapter(MainActivity.this,R.layout.contact_row,contactsList);
        contacts.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newContact = new Intent(MainActivity.this,NewContact.class);
                startActivity(newContact);
            }
        });
    }
}
