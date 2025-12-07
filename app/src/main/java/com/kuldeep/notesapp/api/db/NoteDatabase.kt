package com.kuldeep.notesapp.api.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kuldeep.notesapp.model.NoteResponse

@Database(entities = [NoteResponse::class], version = 1)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}