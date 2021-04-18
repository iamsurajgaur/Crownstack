package com.myapp.crownstack.repo

import android.util.Log
import com.bumptech.glide.load.HttpException
import com.myapp.crownstack.model.SongList
import com.myapp.crownstack.network.SongService
import com.myapp.crownstack.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class SongListRepo()  {

    suspend fun getSongList(): Resource<SongList> {
        val api = SongService()
        return safeApiCall(Dispatchers.IO) {api.getSongList("Michael+jackson")}
    }

    suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> Response<T>): Resource<T> {
        return withContext(dispatcher) {
            try {
                val response = apiCall.invoke()
                Log.e("retrofit_response", response.toString())
                if(response.isSuccessful) {
                    Resource.Success(response.body()!!)
                }
                else{
                    Resource.Error(response.code(),response.body().toString()!!)
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> Resource.NetworkError
                    is HttpException -> {
                        val code = throwable.statusCode
                        val body = throwable.message
                        val errorResponse = body.toString()
                        Resource.Error(code, errorResponse)
                    }
                    else -> {
                        Resource.Error(null, null)
                    }
                }
            }
        }
    }
}