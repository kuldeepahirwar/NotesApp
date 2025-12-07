package com.kuldeep.notesapp.ui.login

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
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
class AuthViewModel @Inject constructor (private val repository: UserRepository): ViewModel() {

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
    fun validateCredentials(username:String,email:String,password:String,isLogin:Boolean):Pair<Boolean,String>{

        var result = Pair(true,"")
        if((!isLogin && TextUtils.isEmpty(username)) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            result = Pair(false,"Please provide the credentials")
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false,"Please provide valid email")
        }
        else if(password.length < 6){
            result = Pair(false,"Password must be at least 6 characters long")
        }
        return result


    }

}