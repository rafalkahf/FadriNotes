package com.example.fadrinotes.Activity.Notes.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fadrinotes.Activity.Notes.Models.NotesModel;
import com.example.fadrinotes.DataBase.DataBaseNotesApp;

import java.util.ArrayList;
import java.util.List;

public class NotesManager {
    private SQLiteDatabase dbHelper;
    private DataBaseNotesApp db;

    public NotesManager(Context context) {
        this.db = new DataBaseNotesApp(context);
        this.dbHelper = db.getWritableDatabase();
    }

    public void addNote(NotesModel note) {
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("headline", note.getHeadLine());
        values.put("content", note.getContent());
        values.put("date", note.getDate());
        values.put("user_id", note.getUserId());

        dbHelper.insert("notes", null, values);
    }

    public NotesModel getNoteById(int id) {
        NotesModel note = null;
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = dbHelper.query("notes", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int noteId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String headline = cursor.getString(cursor.getColumnIndexOrThrow("headline"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));

            note = new NotesModel(noteId, userId, title, headline, content, date);
        }

        cursor.close();
        return note;
    }


    public List<NotesModel> getAllNotes(int userId) {
        List<NotesModel> notesList = new ArrayList<>();
        String selection = "user_id = ?";
        String[] selectionArgs = { String.valueOf(userId) };
        Cursor cursor = dbHelper.query("notes", null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String headline = cursor.getString(cursor.getColumnIndexOrThrow("headline"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                NotesModel note = new NotesModel(id, userId, title, headline, content, date);
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return notesList;
    }




    public List<NotesModel> searchNotes(String query) {
        List<NotesModel> notes = new ArrayList<>();
        String searchQuery = "SELECT * FROM notes WHERE title LIKE ? OR content LIKE ?";
        Cursor cursor = dbHelper.rawQuery(searchQuery, new String[]{"%" + query + "%", "%" + query + "%"});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String headline = cursor.getString(cursor.getColumnIndexOrThrow("headline"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                System.out.println("CheckUser id : " + userId);
                NotesModel note = new NotesModel(id, userId, title, headline, content, date);
                note.setId(id);
                note.setUserId(userId);
                note.setTitle(title);
                note.setHeadLine(headline);
                note.setContent(content);
                note.setDate(date);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    public void delete(int id) {
        dbHelper.delete("notes", "id = ?", new String[]{String.valueOf(id)});
    }

    public void update(NotesModel note) {
        ContentValues values = new ContentValues();
        values.put("id", note.getId());
        values.put("title", note.getTitle());
        values.put("headline", note.getHeadLine());
        values.put("content", note.getContent());
        values.put("date", note.getDate());
        values.put("user_id", note.getUserId());

        dbHelper.update("notes", values, "id = ?", new String[]{String.valueOf(note.getId())});
    }
}
