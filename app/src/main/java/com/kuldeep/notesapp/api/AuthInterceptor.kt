package com.kuldeep.notesapp.api

import com.kuldeep.notesapp.utils.TokenManager
import okhttp3.Interceptor
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager
    // This function intercepts HTTP requests to add an Authorization header if a token is available.
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val requestBuilder = chain.request().newBuilder()
        val token = tokenManager.getToken()
        if(token!=null){
            requestBuilder.addHeader("Authorization","Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
    }
}