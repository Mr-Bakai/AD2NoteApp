package com.hfad.ad2noteapp.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.hfad.ad2noteapp.models.Note;


@Database(entities = {Note.class}, version =  1 )
public abstract class AppDataBase  extends RoomDatabase {
    public abstract NoteDao noteDao();
}
