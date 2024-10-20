package com.dicoding.dicodingeventfavoritefeatures.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.dicodingeventfavoritefeatures.data.local.entity.EventEntity
import com.dicoding.dicodingeventfavoritefeatures.data.local.room.EventDao
import com.dicoding.dicodingeventfavoritefeatures.data.remote.retrofit.ApiService
import com.dicoding.newsapp.utils.AppExecutors
import com.google.gson.internal.GsonBuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val appExecutors: AppExecutors

){
    private val result = MediatorLiveData<Result<List<EventEntity>>>()

    fun getEventName(): LiveData<Result<List<EventEntity>>>{
        result.value = Result.Loading
        val client = apiService.getEvents(1)
        client.enqueue(object : Callback<EventEntity> {
            override fun onResponse(call: Call<EventEntity>, response: Response<EventEntity>) {
                if (response.isSuccessful){
                    val events = response.body()?.events
                    val eventList = ArrayList<EventEntity>()
                    appExecutors.diskIO.execute{
                        events?.forEach { event ->
                            val isFavorite = eventDao.isEventFavorite(event.eventName)

                            val list = EventEntity(
                                event.eventName,
                                event.ownerName
                            )
                        }
                    }
                }
            }
        })
    }
}