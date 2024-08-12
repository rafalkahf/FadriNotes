package com.example.fadrinotes.Activity.Notes.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotesModel {
    private int id;
    private int userId;
    private String title;
    private String headLine;
    private String content;
    private String date;

    public NotesModel(int id, int userId, String title, String headLine, String content, String date) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.headLine = headLine;
        this.content = content;
        this.date = date;
    }

}
