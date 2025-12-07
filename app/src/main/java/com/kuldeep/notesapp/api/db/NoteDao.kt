package com.kuldeep.notesapp.api.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuldeep.notesapp.model.NoteResponse

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNotes(notes: List<NoteResponse>)
    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<NoteResponse>
}