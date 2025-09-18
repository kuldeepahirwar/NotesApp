package com.kuldeep.notesapp.api

import com.kuldeep.notesapp.model.UserRequest
import com.kuldeep.notesapp.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/signup")
    suspend fun signup(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("/login")
    suspend fun login(@Body userRequest: UserRequest): Response<UserResponse>
}