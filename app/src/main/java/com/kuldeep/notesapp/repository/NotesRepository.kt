package com.kuldeep.notesapp.repository

import androidx.lifecycle.MutableLiveData
import com.kuldeep.notesapp.api.NotesApi
import com.kuldeep.notesapp.model.NoteRequest
import com.kuldeep.notesapp.model.NoteResponse
import com.kuldeep.notesapp.utils.NetworkResult
import retrofit2.Response
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesApi: NotesApi) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData: MutableLiveData<NetworkResult<List<NoteResponse>>> get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: MutableLiveData<NetworkResult<String>> get() = _statusLiveData

    // Fetch notes from the API
    suspend fun getNotes(){
        _notesLiveData.postValue(NetworkResult.Loading())
        try {
            val response = notesApi.getNotes()
            if (response.isSuccessful && response.body() != null) {
                _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
            } else {
                _notesLiveData.postValue(NetworkResult.Error("Failed to fetch notes"))
            }
        } catch (e: Exception) {
            _notesLiveData.postValue(NetworkResult.Error("An error occurred: ${e.message}"))
        }
    }
    suspend fun createNote(noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesApi.createdNote(noteRequest)
        handleResponse(response,"Note created")

    }

    suspend fun deleteNote(noteId: String){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesApi.deleteNote(noteId)
        handleResponse(response,"Note deleted")
    }
    suspend fun updateNote(noteId: String, noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesApi.updateNote(noteId,noteRequest)
        handleResponse(response,"Note updated")
    }

    private fun handleResponse(response: Response<NoteResponse>,message:String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong") )
        }
    }
}