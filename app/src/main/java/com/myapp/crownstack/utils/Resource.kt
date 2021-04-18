package com.myapp.crownstack.utils

sealed class Resource<out T> {
     data class Loading<out T>(val data: T?) : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val code: Int? = null, val error: String? = null) :
        Resource<Nothing>()

    object NetworkError : Resource<Nothing>()



}