package com.jay.emergencycontact;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NewContact extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageButton save = findViewById(R.id.save_new_contact);
        final EditText name = findViewById(R.id.new_contact_name);
        final EditText phone = findViewById(R.id.phone_number);
        final ContactsHelper helper = ContactsHelper.getInstance(NewContact.this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString();
                String phoneStr = phone.getText().toString();
                if (nameStr.equals("")) {
                    Toast.makeText(NewContact.this,
                            "The name of your contact can't be empty",Toast.LENGTH_LONG).show();
                }
                else if (phoneStr.equals("")) {
                    Toast.makeText(NewContact.this,
                            "The phone number of your contact can't be empty",Toast.LENGTH_LONG).show();
                }
                else {
                    long rowId = helper.insertContact(nameStr,phoneStr);
                    Intent viewIntent = new Intent(NewContact.this,ViewContact.class);
                    viewIntent.putExtra(ViewContact.ROW_ID_KEY,rowId);
                    startActivity(viewIntent);
                    finish();
                }
            }
        });
    }
}
