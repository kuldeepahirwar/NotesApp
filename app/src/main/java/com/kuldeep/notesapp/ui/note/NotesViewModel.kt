package com.kuldeep.notesapp.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuldeep.notesapp.model.NoteRequest
import com.kuldeep.notesapp.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository): ViewModel() {

    val noteLiveData = notesRepository.notesLiveData
    val statusLiveData = notesRepository.statusLiveData
    fun getNote() {
        viewModelScope.launch {
            notesRepository.getNotes()
        }
    }
    fun createNote(noteRequest: NoteRequest){
        viewModelScope.launch {
            notesRepository.createNote(noteRequest)
        }
    }
    fun deleteNote(noteId: String){
        viewModelScope.launch {
            notesRepository.deleteNote(noteId)
        }
    }
    fun updateNote(noteId: String,noteRequest: NoteRequest){
        viewModelScope.launch {
            notesRepository.updateNote(noteId,noteRequest)
        }
    }

}