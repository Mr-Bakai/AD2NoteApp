package com.hfad.ad2noteapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;


@Entity
public class Note implements Serializable {

    @PrimaryKey
    @NonNull
    private String noteId;
    private String title;
    private String date;


    public Note(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public Note() {
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
