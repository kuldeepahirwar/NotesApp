package com.kuldeep.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes") // Define Room entity for local database
data class NoteResponse(
    val __v: Int,
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val createdAt: String,
    val description: String,
    val title: String,
    val updatedAt: String,
    val userId: String
)