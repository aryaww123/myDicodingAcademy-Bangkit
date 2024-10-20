package com.dicoding.restaurantreview.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.restaurantreview.data.response.CustomerReviewsItem
import com.dicoding.restaurantreview.data.response.PostReviewResponse
import com.dicoding.restaurantreview.data.response.Restaurant
import com.dicoding.restaurantreview.data.response.RestaurantResponse
import com.dicoding.restaurantreview.data.retrofit.ApiConfig
//import com.dicoding.restaurantreview.ui.MainActivity.Companion
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Tag

class MainViewModel : ViewModel(){

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        _isLoading.value = true
//        showLoading(true)
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : retrofit2.Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                _isLoading.value = false
//                showLoading(false)
                if (response.isSuccessful) {
                    _restaurant.value = response.body()?.restaurant
                    _listReview.value = response.body()?.restaurant?.customerReviews

//                    val responseBody = response.body()
//
//                    if (responseBody != null){
//                        setRestaurantData(responseBody.restaurant)
//                        setReviewData(responseBody.restaurant.customerReviews)
//                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable){
                _isLoading.value = false
//                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }

    fun postReview(review: String){
        _isLoading.value = true
//        showLoading(true)
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID,"Mas Aryaww", review)
        client.enqueue(object : retrofit2.Callback<PostReviewResponse> {
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
//                showLoading(false)
                // val responseBody = response.body()
                _isLoading.value = false

                if (response.isSuccessful){
                    _listReview.value = response.body()?.customerReviews
//                    setReviewData(responseBody.customerReviews)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable){
//                showLoading(false)
                _isLoading.value=false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}