package com.deepk.promtexa

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ChatApi {

    @POST("/api/chat")
    fun sendMessage(@Body request: Map<String, String>): Call<ChatResponse>
}