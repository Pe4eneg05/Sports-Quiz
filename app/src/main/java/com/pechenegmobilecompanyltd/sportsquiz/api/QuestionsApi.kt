package com.pechenegmobilecompanyltd.sportsquiz.api

import com.pechenegmobilecompanyltd.sportsquiz.entity.ListQuestions
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface QuestionsApi {

    @GET
    suspend fun questionList(@Url url: String): ListQuestions
}

val retrofit: QuestionsApi = Retrofit.Builder()
    .baseUrl("https://opentdb.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(QuestionsApi::class.java)