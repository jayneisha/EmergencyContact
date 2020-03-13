package com.jay.emergencycontact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class ViewContact extends AppCompatActivity {
    public static final String ROW_ID_KEY = "row_id";
    private long rowId;
    private boolean editing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final ImageButton dial = findViewById(R.id.dial_btn);
        final ImageButton edit_save = findViewById(R.id.edit_save);
        final EditText name = findViewById(R.id.view_contact_name);
        final EditText phone = findViewById(R.id.view_phone_number);

        if (savedInstanceState != null) {
            rowId = savedInstanceState.getLong(ROW_ID_KEY,-1);
        }
        else {
            Intent intent = getIntent();
            rowId = intent.getLongExtra(ROW_ID_KEY, -1);
        }
        final ContactsHelper helper = ContactsHelper.getInstance(ViewContact.this);

        if (rowId != -1) {
            final Contact contact = helper.getContact(rowId);
            name.setText(contact.getName());
            phone.setText(contact.getPhone());

            dial.setOnClickListener(new View.OnClickListener() { //new
                @Override
                public void onClick(View v) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel://"+contact.getPhone()));
                    startActivity(dialIntent);
                }
            });

            edit_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editing = !editing;
                    if (editing) {
                        name.setEnabled(true);
                        phone.setEnabled(true);
                        edit_save.setImageResource(R.drawable.ic_save);
                        dial.setVisibility(View.INVISIBLE); //new
                    }
                    else {
                        name.setEnabled(false);
                        phone.setEnabled(false);
                        edit_save.setImageResource(R.drawable.ic_edit);
                        String nameStr = name.getText().toString();
                        String phoneStr = phone.getText().toString();
                        helper.updateContact(rowId,nameStr,phoneStr);
                        contact.setName(nameStr);
                        contact.setPhone(phoneStr);
                        dial.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        else {
            Intent mainIntent = new Intent(ViewContact.this,MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(ROW_ID_KEY,rowId);
        super.onSaveInstanceState(outState);
    }
}
