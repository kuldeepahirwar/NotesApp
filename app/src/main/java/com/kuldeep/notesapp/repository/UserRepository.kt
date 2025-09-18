package com.kuldeep.notesapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kuldeep.notesapp.api.UserApi
import com.kuldeep.notesapp.model.UserRequest
import com.kuldeep.notesapp.model.UserResponse
import com.kuldeep.notesapp.utils.Constant.TAG
import com.kuldeep.notesapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _userResponseData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseData:MutableLiveData<NetworkResult<UserResponse>> get() = _userResponseData
    suspend fun signup(userRequest: UserRequest){
        _userResponseData.postValue(NetworkResult.Loading())
        val response = userApi.signup(userRequest)
        handleResponse(response)
    }
    suspend fun login(userRequest: UserRequest){
        val response = userApi.login(userRequest)
        handleResponse(response)
    }
    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.string())
            _userResponseData.postValue(NetworkResult.Error(errorObj.getString("error")))
        } else {
            _userResponseData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}