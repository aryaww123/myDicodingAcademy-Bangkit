package com.dicoding.dicodingeventfavoritefeatures.data.remote.retrofit

import com.dicoding.dicodingeventfavoritefeatures.data.remote.response.Response
import com.dicoding.dicodingeventfavoritefeatures.data.remote.response.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.*

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int
    ): Call<Response>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<DetailResponse>
}