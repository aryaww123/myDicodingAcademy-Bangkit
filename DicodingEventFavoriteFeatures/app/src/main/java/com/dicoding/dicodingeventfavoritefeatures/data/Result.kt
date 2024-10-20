package com.dicoding.dicodingeventfavoritefeatures.data

import kotlin.Result
//import com.dicoding.dicodingeventfavoritefeatures.data

sealed class Result <out R> private constructor(){
    data class Success<out T>(val data: T) : com.dicoding.dicodingeventfavoritefeatures.data.Result<T>()
    data class Error(val error: String) : com.dicoding.dicodingeventfavoritefeatures.data.Result<Nothing>()
    object Loading : com.dicoding.dicodingeventfavoritefeatures.data.Result<Nothing>()
}