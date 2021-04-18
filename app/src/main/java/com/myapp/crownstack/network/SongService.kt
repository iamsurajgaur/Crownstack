package com.myapp.crownstack.network

import com.myapp.crownstack.model.SongList
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SongService {

    companion object{
        operator fun invoke():SongService{
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://itunes.apple.com/")
                .build()
                .create(SongService::class.java)
        }
    }

    @GET("search")
    suspend fun getSongList(@Query("term") term:String): Response<SongList>

}