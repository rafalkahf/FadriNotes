package com.example.fadrinotes.Activity.ActivityMain;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fadrinotes.Activity.AuthentUser.AuthAct.AuthActivity;
import com.example.fadrinotes.Activity.Notes.Adapter.NoteAdapter;
import com.example.fadrinotes.Activity.Notes.Repository.NotesManager;
import com.example.fadrinotes.Activity.Notes.ViewModel.NotesViewModel;
import com.example.fadrinotes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    NotesViewModel notesViewModel;
    TextView textGreeting;
    RecyclerView recyclerView;
    EditText searchNotes;
    NoteAdapter notesAdapter;
    FloatingActionButton addNewNotes, logoutButton;
    SharedPreferences sharedPreferences;
    NotesManager notesManager;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesManager = new NotesManager(this);
        notesViewModel = new NotesViewModel(this);
        textGreeting = findViewById(R.id.textGreet);
        searchNotes = findViewById(R.id.edtiTextSearch);
        addNewNotes = findViewById(R.id.floatAdd);
        logoutButton = findViewById(R.id.floatProfile);
        notesAdapter = new NoteAdapter(this, notesViewModel);
        recyclerView = findViewById(R.id.myRecycleView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesInput.class);
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences("LocalData", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        textGreeting.setText("Hello, " + userName);

        notesViewModel.getNotesLiveData().observe(this, notes -> {
            notesAdapter.setNotes(notes);
        });

        addNewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                startActivity(new Intent(MainActivity.this, NotesInput.class).putExtra("NOTE_ID", -1));
                finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        searchNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notesViewModel.searchNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fadri Notes")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("OK", (dialog, which) -> {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getWindow().setLayout(
                (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.9),
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}