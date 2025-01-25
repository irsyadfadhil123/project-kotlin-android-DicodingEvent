package com.example.dicodingevent.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.data.response.EventResponseSingle
import com.example.dicodingevent.data.retrofit.ApiConfig
import com.example.dicodingevent.ui.fragment.upcoming.UpcomingViewModel
import com.example.dicodingevent.ui.fragment.upcoming.UpcomingViewModel.Companion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailViewModel : ViewModel() {
    private val _eventData = MutableLiveData<EventItem?>()
    val eventData: LiveData<EventItem?> = _eventData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    companion object {
        private const val TAG = "EventDetailViewModel"
        private var currentEventId: Int? = null
    }

    fun detailEvent(eventId: Int) {
        if (currentEventId == eventId && _eventData.value != null) {
            return
        }
        currentEventId = eventId
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEventDetail(eventId)
        client.enqueue(object : Callback<EventResponseSingle> {
            override fun onResponse(call: Call<EventResponseSingle>, response: Response<EventResponseSingle>) {
                _message.value = ""
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (response.body()?.event == null) {
                            _message.value = "Sedang tidak ada detail Event"
                        } else {
                            _eventData.value = response.body()?.event
                        }
                    }
                }
            }

            override fun onFailure(call: Call<EventResponseSingle>, t: Throwable) {
                _isLoading.value = false
                _message.value = "Gagal memuat Event. Cek kembali koneksi anda"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}