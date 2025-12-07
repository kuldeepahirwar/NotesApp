package com.kuldeep.notesapp.repository

import androidx.lifecycle.MutableLiveData
import com.kuldeep.notesapp.api.NotesApi
import com.kuldeep.notesapp.api.db.NoteDao
import com.kuldeep.notesapp.model.NoteRequest
import com.kuldeep.notesapp.model.NoteResponse
import com.kuldeep.notesapp.utils.NetworkResult
import com.kuldeep.notesapp.utils.NetworkUtils
import retrofit2.Response
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesApi: NotesApi,
    private val noteDao: NoteDao,
    private val networkUtils: NetworkUtils) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData: MutableLiveData<NetworkResult<List<NoteResponse>>> get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: MutableLiveData<NetworkResult<String>> get() = _statusLiveData

    //This function fetches from network and updates the local DB
    suspend fun getNotes(){
        if(networkUtils.isNetworkAvailable()) {
            _notesLiveData.postValue(NetworkResult.Loading())
            try {
                val response = notesApi.getNotes()
                if (response.isSuccessful && response.body() != null) {
                    noteDao.insertAllNotes(response.body()!!)
                    _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    _notesLiveData.postValue(NetworkResult.Error("Failed to fetch notes ${response.message()}"))
                }
            } catch (e: Exception) {
                _notesLiveData.postValue(NetworkResult.Error("An error occurred: ${e.message}"))
            }
        }else{
            val cachedNotes = noteDao.getAllNotes()
            if(cachedNotes.isNotEmpty()){
                _notesLiveData.postValue(NetworkResult.Success(cachedNotes))
            }else{
                _notesLiveData.postValue(NetworkResult.Error("No internet connection and no cached data available"))
            }
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

    private fun handleResponse(response: Response<NoteResponse>, message:String) {
        if (response.isSuccessful && response.body() != null) {
            //getNotes()
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong") )
        }
    }
}