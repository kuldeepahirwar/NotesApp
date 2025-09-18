package com.kuldeep.notesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuldeep.notesapp.model.UserRequest
import com.kuldeep.notesapp.model.UserResponse
import com.kuldeep.notesapp.repository.UserRepository
import com.kuldeep.notesapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val repository: UserRepository):ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>> get() = repository.userResponseData
    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            repository.signup(userRequest)
        }
    }
    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            repository.login(userRequest)
        }
    }

}