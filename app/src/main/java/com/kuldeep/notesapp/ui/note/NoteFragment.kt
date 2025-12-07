package com.kuldeep.notesapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.kuldeep.notesapp.databinding.FragmentNoteBinding
import com.kuldeep.notesapp.model.NoteRequest
import com.kuldeep.notesapp.model.NoteResponse
import com.kuldeep.notesapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private var note: NoteResponse?=null

    private val notesViewModel by viewModels<NotesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        setInitialData()
        bindHandler()
        bindObserver()
        return binding.root
    }

    private fun bindHandler() {
        binding.btnDelete.setOnClickListener {
            note?.let {
                notesViewModel.deleteNote(it!!._id)
            }
        }
        binding.btnSubmit.setOnClickListener {
            val title = binding.txtTitle.text.toString().trim()
            val description = binding.txtDescription.text.toString().trim()
            val noteRequest = NoteRequest(title, description)
            if(note!=null){
                // update note
                notesViewModel.updateNote(note!!._id,noteRequest)
            }else{
                // add note
                notesViewModel.createNote(noteRequest)
            }
        }
    }
    private fun bindObserver() {
        notesViewModel.statusLiveData.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResult.Loading->{
                }
                is NetworkResult.Success->{
                    findNavController().popBackStack()
                }
                is NetworkResult.Error->{
                }
            }
        }

    }

    private fun setInitialData() {
        var jsonResponse = arguments?.get("note")
        if(jsonResponse!=null){
            note = Gson().fromJson(jsonResponse.toString(), NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }
        }
        else{
            binding.addEditText.text = "Add Note"
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}