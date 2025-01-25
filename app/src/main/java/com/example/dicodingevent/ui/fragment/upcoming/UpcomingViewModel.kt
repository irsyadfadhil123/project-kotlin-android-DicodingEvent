package com.example.dicodingevent.ui.fragment.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

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
                _message.value = ""
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (response.body()?.listEvents.isNullOrEmpty()) {
                            _message.value = "Sedang tidak ada Event yang akan datang"
                        } else {
                            _eventItem.value = response.body()?.listEvents
                        }
                    }
                } else {
                    _message.value = "Event tidak ditemukan"
                    Log.e(TAG, "onResponse Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = "Gagal memuat Event. Cek kembali koneksi anda"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}