package com.example.fadrinotes.Activity.AuthentUser.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.fadrinotes.DataBase.DataBaseNotesApp;
import com.example.fadrinotes.Activity.AuthentUser.DataModels.Users;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserManager {
    private final SQLiteDatabase database;
    private final DataBaseNotesApp dbHelper;
    Context context;

    public UserManager(Context context) {
        dbHelper = new DataBaseNotesApp(context);
        database = dbHelper.getWritableDatabase();
        this.context = context;
    }

    public boolean registerUser(Users users) {
        if (!checkEmailExists(users.getEmail())) {

            ContentValues values = new ContentValues();
            values.put("email", users.getEmail());
            values.put("password", hashPassword(users.getPassword()));
            long result = database.insert("users", null, values);
            return result != -1;

        } else {
            return false;
        }
    }

    private boolean checkEmailExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"id"};
        String selection = "email = ?";
        String[] selectionArgs = {email};

        try (Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null)) {
            boolean userExists = cursor.getCount() > 0;
            cursor.close();
            return userExists;
        }
    }

    public void checkEmailForRegister(String email){
        if (!checkEmailExists(email)) {
            showToast(context, "Email Sudah terdaftar, gunakan Email lain");
        }
    }

    public void checkEmailForLogin(String email){
        if (!checkEmailExists(email)) {
            showToast(context, "Email belum terdaftar, mohon registrasi terlebih dahulu");
        }
    }
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean loginUser(String email, String password) {
        String hashedPassword = hashPassword(password);
        String[] columns = {"id"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, hashedPassword};

        try (Cursor cursor = database.query("users", columns, selection, selectionArgs, null, null, null)) {
            boolean userExists = cursor.getCount() > 0;
            cursor.close();
            return userExists;
        }
    }

    public Users getUserByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"id", "email", "password"};
        String selection = "email = ?";
        String[] selectionArgs = {email};
        try (Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null)) {
            if (cursor.moveToFirst()) {
                Users users = new Users(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password"))
                );
                cursor.close();
                return users;
            } else {
                cursor.close();
            }
        }

        return null;
    }


    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
