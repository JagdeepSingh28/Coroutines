package com.example.coroutines.sample1.retro

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {

    @GET("/todos")
    fun getTodos(): Call<List<Todo>>

//    @GET("/todos")
//    suspend fun getTodos(): Response<List<Todo>>

}