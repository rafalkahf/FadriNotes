package com.example.fadrinotes.Activity.Notes.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fadrinotes.Activity.Notes.Models.NotesModel;
import com.example.fadrinotes.Activity.Notes.Repository.NotesManager;

import java.util.List;

public class NotesViewModel extends ViewModel {
    private NotesManager notesManager;
    private MutableLiveData<List<NotesModel>> notesLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> operationStatus = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<NotesModel> noteLiveData = new MutableLiveData<>();

    SharedPreferences sharedPreferences;
    private int userId;

    public NotesViewModel(Context context) {
        notesManager = new NotesManager(context.getApplicationContext());
        sharedPreferences = context.getSharedPreferences("LocalData", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        loadNotes();
    }

    public LiveData<List<NotesModel>> getNotesLiveData() {
        return notesLiveData;
    }

    public LiveData<Boolean> getOperationStatus() {
        return operationStatus;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<NotesModel> getNoteById(int noteId) {
        MutableLiveData<NotesModel> noteLiveData = new MutableLiveData<>();
        new Thread(() -> {
            NotesModel note = notesManager.getNoteById(noteId);
            noteLiveData.postValue(note);
        }).start();
        return noteLiveData;
    }

    public void addNote(NotesModel note) {
        try {
            note.setUserId(userId);
            notesManager.addNote(note);
            operationStatus.setValue(true);
            loadNotes();
        } catch (Exception e) {
            operationStatus.setValue(false);
            errorMessage.setValue(e.getMessage());
        }
    }

    public void updateNote(NotesModel note) {
        try {
            note.setUserId(userId);
            notesManager.update(note);
            operationStatus.setValue(true);
            loadNotes();
        } catch (Exception e) {
            operationStatus.setValue(false);
            errorMessage.setValue(e.getMessage());
        }
    }

    public void deleteNote(int noteId) {
        try {
            notesManager.delete(noteId);
            operationStatus.setValue(true);
            loadNotes();
        } catch (Exception e) {
            operationStatus.setValue(false);
            errorMessage.setValue(e.getMessage());
        }
    }

    public void searchNotes(String query) {
        new Thread(() -> {
            List<NotesModel> notes = notesManager.searchNotes(query);
            notesLiveData.postValue(notes);
        }).start();
    }

    private void loadNotes() {
        new Thread(() -> notesLiveData.postValue(notesManager.getAllNotes(userId))).start();
    }
}
