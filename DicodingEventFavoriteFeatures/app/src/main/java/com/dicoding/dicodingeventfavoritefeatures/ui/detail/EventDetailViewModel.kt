package com.dicoding.dicodingeventfavoritefeatures.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingeventfavoritefeatures.data.remote.response.DetailResponse
import com.dicoding.dicodingeventfavoritefeatures.data.remote.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailViewModel (private val apiService: ApiService) : ViewModel() {
    private val _detailEvent = MutableLiveData<DetailResponse>()
    private val detailEvent: LiveData<DetailResponse> = _detailEvent

    fun getDetailEvent(eventId: String): LiveData<DetailResponse>{
        viewModelScope.launch {
            try {
                apiService.getDetailEvent(eventId).enqueue(object : Callback<DetailResponse>{
                    override fun onResponse(
                        call: Call<DetailResponse>,
                        response: Response<DetailResponse>
                    ) {
                        if (response.isSuccessful) {
                            _detailEvent.value = response.body()
                        } else {
                            Log.e("EventDetailViewModel", "Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                        Log.e("EventDetailViewModel", "Error: ${t.message}")
                    }
                })
            } catch (e: Exception){
                Log.e("EventDetailViewModel", "Error: ${e.message}")
            }
        }
        return detailEvent
    }

    class EventDetailViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventDetailViewModel(apiService) as T
        }
    }
}