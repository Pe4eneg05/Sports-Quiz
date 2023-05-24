package com.pechenegmobilecompanyltd.sportsquiz.ui

import androidx.lifecycle.ViewModel
import com.pechenegmobilecompanyltd.sportsquiz.data.RepositoryApi
import com.pechenegmobilecompanyltd.sportsquiz.entity.Question

class ViewModel private constructor(private val repository: RepositoryApi) : ViewModel() {
    constructor() : this(RepositoryApi())

    suspend fun loadQuestionsList(url: String): List<Question> {
        return RepositoryApi().getQuestionList(url)
    }
}

