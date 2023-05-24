package com.pechenegmobilecompanyltd.sportsquiz.data

import com.pechenegmobilecompanyltd.sportsquiz.api.retrofit
import com.pechenegmobilecompanyltd.sportsquiz.entity.Question

class RepositoryApi {

    suspend fun getQuestionList(url: String): List<Question> {
        return retrofit.questionList(url).results.shuffled()
    }
}