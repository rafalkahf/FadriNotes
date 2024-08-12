package com.example.fadrinotes.Activity.ActivityMain;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fadrinotes.Activity.Notes.Models.NotesModel;
import com.example.fadrinotes.Activity.Notes.Reminder.Reminder;
import com.example.fadrinotes.Activity.Notes.ViewModel.NotesViewModel;
import com.example.fadrinotes.R;
import com.example.fadrinotes.databinding.ActivityMainBinding;
import com.example.fadrinotes.databinding.ActivityNotesInputBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotesInput extends AppCompatActivity {

    ImageButton backButton;
    EditText titleEditText, headingEditText, contentEditText;
    TextView reminder;
    NotesViewModel notesViewModel;
    SharedPreferences sharedPreferences;
    int noteId;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_input);
        backButton = findViewById(R.id.backImageButton);
        titleEditText = findViewById(R.id.editTextTitle);
        headingEditText = findViewById(R.id.EditTextHeadline);
        contentEditText = findViewById(R.id.editTextContent);
        reminder = findViewById(R.id.textViewReminder);
        notesViewModel = new NotesViewModel(this);
        sharedPreferences = getSharedPreferences("LocalData", MODE_PRIVATE);
        createNotif();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("NOTE_ID")) {
            noteId = intent.getIntExtra("NOTE_ID", -1);
            if (noteId != -1) {
                notesViewModel.getNoteById(noteId).observe(this, note -> {
                    if (note != null) {
                        titleEditText.setText(note.getTitle());
                        headingEditText.setText(note.getHeadLine());
                        contentEditText.setText(note.getContent());
                        reminder.setText(note.getDate());
                    }
                });
            }
        }

        backButton.setOnClickListener(v -> {

            if (!titleEditText.getText().toString().isEmpty()){

                String date = reminder.getText().toString();
                String title = titleEditText.getText().toString();
                String heading = headingEditText.getText().toString();
                String content = contentEditText.getText().toString();
                int userID = sharedPreferences.getInt("userId", 0);

                if (noteId == -1){
                    NotesModel notesModel = new NotesModel(0, userID, title, heading, content, date);
                    notesViewModel.addNote(notesModel);

                    notesViewModel.getOperationStatus().observe(this, status -> {
                        if (status) {
                            Toast.makeText(NotesInput.this, "Berhasil Menambahkan Catatan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NotesInput.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(NotesInput.this, "Gagal Menambahkan Catatan", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    NotesModel notesModel = new NotesModel(noteId, userID, title, heading, content, date);
                    notesViewModel.updateNote(notesModel);
                    notesViewModel.getOperationStatus().observe(this, status -> {
                        if (status) {
                            Toast.makeText(NotesInput.this, "Berhasil update Catatan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NotesInput.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(NotesInput.this, "Gagal update Catatan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else {
                startActivity(new Intent(NotesInput.this, MainActivity.class));
                finish();

            }
        });
        reminder.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        Calendar currentCalendar = Calendar.getInstance();
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);
        int day = currentCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    Calendar waktuPilih = Calendar.getInstance();
                    waktuPilih.set(year1, month1, dayOfMonth);

                    if (waktuPilih.before(Calendar.getInstance())) {
                        Toast.makeText(this, "Tanggal tidak bisa sebelum tanggal saat ini.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    showTimePickerDialog(waktuPilih);
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void showTimePickerDialog(Calendar date) {
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date.set(Calendar.MINUTE, minute1);
                    setAlarm(date);
                },
                hour, minute, true);

        timePickerDialog.show();
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setAlarm(Calendar date) {
        Intent intent = new Intent(this, Reminder.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
        }

        Toast.makeText(this, "Alarm diatur pada " + date.getTime(), Toast.LENGTH_SHORT).show();
    }


    private void createNotif(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Reminder Notification";
            String desc = "Fadri Notes";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            android.app.NotificationChannel channel = new android.app.NotificationChannel("notification", name, importance);
            channel.setDescription(desc);
        }
    }
}
