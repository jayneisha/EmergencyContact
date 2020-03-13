package com.jay.emergencycontact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class ContactsHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase db = null;
    public static final String DATABASE_NAME = "contacts_db";
    public static final int DATABASE_VERSION = 1;
    private  static ContactsHelper instance;

    private ContactsHelper(@Nullable Context context, @Nullable String name,
                           @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = getWritableDatabase();
    }

    public static synchronized ContactsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ContactsHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactTable.SQL_CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertContact(String name,String phone) {
        ContentValues values = new ContentValues();
        values.put(ContactTable.COL_CONTACT_NAME,name);
        values.put(ContactTable.COL_PHONE_NUMBER,phone);
        return db.insert(ContactTable.TABLE_NAME,null,values);
    }

    public void updateContact(long rowId,String name,String phone) {
        ContentValues values = new ContentValues();
        values.put(ContactTable.COL_CONTACT_NAME,name);
        values.put(ContactTable.COL_PHONE_NUMBER,phone);
        String whereClause = ContactTable._ID + " = ?";
        String[] whereArgs = {""+rowId};
        db.update(ContactTable.TABLE_NAME,values,whereClause,whereArgs);
    }

    public void deleteContact(long rowId) {
        String whereClause = ContactTable._ID + " = ?";
        String[] whereArgs = {""+rowId};
        db.delete(ContactTable.TABLE_NAME,whereClause,whereArgs);
    }

    public String[] getNameAndPhoneFromCursor(Cursor contactCursor) {
        String[] results = new String[2];
        int nameIndex = contactCursor.getColumnIndex(ContactTable.COL_CONTACT_NAME);
        int phoneIndex = contactCursor.getColumnIndex(ContactTable.COL_PHONE_NUMBER);
        results[0] = contactCursor.getString(nameIndex);
        results[1] = contactCursor.getString(phoneIndex);
        return results;
    }

    public Contact getContact(long rowId) {
        String[] columns = {ContactTable.COL_CONTACT_NAME,ContactTable.COL_PHONE_NUMBER};
        String where = ContactTable._ID + " = ?";
        String[] whereArgs = {""+rowId};
        Cursor contactCursor = db.query(ContactTable.TABLE_NAME,columns,where,whereArgs,
                null,null,null);
        Contact contact = null;
        if (contactCursor.moveToNext()) {
            String[] results = getNameAndPhoneFromCursor(contactCursor);
            contact = new Contact(rowId,results[0],results[1]);
        }
        contactCursor.close();
        return contact;
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        String[] columns = {ContactTable._ID,ContactTable.COL_CONTACT_NAME,ContactTable.COL_PHONE_NUMBER};
        String orderBy = ContactTable.COL_CONTACT_NAME;
        Cursor contactCursor = db.query(ContactTable.TABLE_NAME,columns,
                null,null,null,null,orderBy);
        while (contactCursor.moveToNext()) {
            String[] results = getNameAndPhoneFromCursor(contactCursor);
            long rowId = contactCursor.getLong(contactCursor.getColumnIndex(ContactTable._ID));
            contacts.add(new Contact(rowId,results[0],results[1]));
        }
        return contacts;
    }

    static class ContactTable implements BaseColumns {
        static final String TABLE_NAME = "contacts";
        static final String COL_CONTACT_NAME = "contact_name";
        static final String COL_PHONE_NUMBER = "phone_number";

        static final String SQL_CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS "+
                TABLE_NAME + " ("+
                ContactTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_CONTACT_NAME + " TEXT, " +
                COL_PHONE_NUMBER + " TEXT )";
    }
}
