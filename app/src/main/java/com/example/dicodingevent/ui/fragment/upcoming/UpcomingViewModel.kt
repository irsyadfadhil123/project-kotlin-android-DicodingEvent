package com.example.dicodingevent.ui.fragment.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.response.EventResponse
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _eventItem = MutableLiveData<List<EventItem>>()
    val eventItem: LiveData<List<EventItem>> = _eventItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _eventId = MutableLiveData<String>()
    val eventId: LiveData<String> = _eventId

    companion object {
        private const val TAG = "UpcomingViewModel"
    }

    init {
        upcomingEvents()
    }

    private fun upcomingEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUpcomingEvent()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _eventItem.value = response.body()?.listEvents
                    }
                } else {
                    Log.e(TAG, "onResponse Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}