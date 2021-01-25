package com.hfad.ad2noteapp.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hfad.ad2noteapp.models.Note;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDao {

@Query("SELECT * FROM note")
       List<Note> getAll();

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note")
    void deleteComplete();

    @Query("DELETE FROM note WHERE id = :userId")
    void deleteByUserId(long userId);
}
