package com.example.fadrinotes.Activity.Notes.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fadrinotes.Activity.ActivityMain.NotesInput;
import com.example.fadrinotes.Activity.Notes.Models.NotesModel;
import com.example.fadrinotes.Activity.Notes.Repository.NotesManager;
import com.example.fadrinotes.Activity.Notes.ViewModel.NotesViewModel;
import com.example.fadrinotes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<NotesModel> notes = new ArrayList<>();

    private Context context;

    private NotesViewModel notesViewModel;

    public NoteAdapter(Context context,  NotesViewModel notesViewModel) {
        this.context = context;
        this.notesViewModel = notesViewModel;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notes, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NotesModel note = notes.get(position);
        holder.itemView.setTag(note.getId());

        int firstIndex = position * 2;
        int secondIndex = firstIndex + 1;

        if (firstIndex < notes.size()) {
            NotesModel noteFirst = notes.get(firstIndex);
            holder.tittlNotesFirst.setText(noteFirst.getTitle());
            holder.headlineNotesFirst.setText(noteFirst.getHeadLine());
            holder.contentNotesFirst.setText(noteFirst.getContent());
            holder.dateNotesFirst.setText(noteFirst.getDate());
            holder.cardViewFirst.setVisibility(View.VISIBLE);

            holder.cardViewFirst.setOnLongClickListener(v -> {
                handleLongClick(v, noteFirst.getId());
                return true;
            });

            holder.cardViewFirst.setOnClickListener(v -> {
                handleItemClick(noteFirst.getId());
            });

        } else {
            holder.cardViewFirst.setVisibility(View.GONE);
        }

        if (secondIndex < notes.size()) {
            NotesModel noteSecond = notes.get(secondIndex);
            holder.tittlNotesSec.setText(noteSecond.getTitle());
            holder.headlineNotesSec.setText(noteSecond.getHeadLine());
            holder.contentNotesSec.setText(noteSecond.getContent());
            holder.dateNotesSec.setText(noteSecond.getDate());
            holder.cardViewSec.setVisibility(View.VISIBLE);

            holder.cardViewSec.setOnLongClickListener(v -> {
                handleLongClick(v, noteSecond.getId());
                return true;
            });

            holder.cardViewSec.setOnClickListener(v -> {
                handleItemClick(noteSecond.getId());
            });

        } else {
            holder.cardViewSec.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (notes.size() + 1) / 2;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNotes(List<NotesModel> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tittlNotesFirst, tittlNotesSec, headlineNotesFirst, headlineNotesSec,
                contentNotesFirst, contentNotesSec, dateNotesFirst, dateNotesSec;
        View cardViewFirst, cardViewSec;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            tittlNotesFirst = itemView.findViewById(R.id.noteTitle1);
            headlineNotesFirst = itemView.findViewById(R.id.noteHeadLine1);
            contentNotesFirst = itemView.findViewById(R.id.contentNotes1);
            dateNotesFirst = itemView.findViewById(R.id.noteDate1);

            tittlNotesSec = itemView.findViewById(R.id.noteTitle2);
            headlineNotesSec = itemView.findViewById(R.id.noteHeadLine2);
            contentNotesSec = itemView.findViewById(R.id.noteContent2);
            dateNotesSec = itemView.findViewById(R.id.noteDate2);

            cardViewFirst = itemView.findViewById(R.id.cardViewFirst);
            cardViewSec = itemView.findViewById(R.id.cardViewSec);
        }
    }

    private void handleLongClick(View view, int noteId) {
        view.animate().scaleX(1f).scaleY(1f).setDuration(200).start();

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }

        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu);
        popupMenu.show();

        popupMenu.setOnDismissListener(menu -> view.animate().scaleX(1f).scaleY(1f).setDuration(200).start());
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemMenu = item.getItemId();
            if (itemMenu == R.id.actionDelete) {
                notesViewModel.deleteNote(noteId);
            }
            return true;
        });
    }

    private void handleItemClick(int noteId) {
        Intent intent = new Intent(context, NotesInput.class);
        intent.putExtra("NOTE_ID", noteId);
        context.startActivity(intent);
    }
}
