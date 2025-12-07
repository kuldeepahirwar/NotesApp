package com.kuldeep.notesapp.api

import com.kuldeep.notesapp.model.NoteRequest
import com.kuldeep.notesapp.model.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApi {

    @POST("/note")
    suspend fun createdNote(@Body noteRequest: NoteRequest): Response<NoteResponse>

    @GET("/note")
    suspend fun getNotes(): Response<List<NoteResponse>>

    @PUT("/note/delete/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String): Response<NoteResponse>

    @PUT("/note/{noteId}")
    suspend fun updateNote(@Path("noteId") noteId: String, @Body noteRequest: NoteRequest): Response<NoteResponse>
}