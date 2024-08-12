package com.example.fadrinotes.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseNotesApp extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fadriApp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTES = "notes";
    private static final String TABLE_USERS = "users";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, password TEXT)";
    private static final String CREATE_TABLE_NOTES  =
            "CREATE TABLE " + TABLE_NOTES + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, headline TEXT, content TEXT, date TEXT, user_id INTEGER, FOREIGN KEY(user_id) REFERENCES users(id))";


    public DataBaseNotesApp(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }
}
