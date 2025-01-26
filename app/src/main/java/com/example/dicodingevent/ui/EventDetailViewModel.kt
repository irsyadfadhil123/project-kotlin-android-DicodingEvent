package com.example.dicodingevent.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.data.response.EventResponseSingle
import com.example.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailViewModel : ViewModel() {
    private val _eventData = MutableLiveData<EventItem?>()
    val eventData: LiveData<EventItem?> = _eventData

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    companion object {
        private const val TAG = "EventDetailViewModel"
        private var currentEventId: Int? = null
    }

    fun retryDetailEvent() {
        currentEventId?.let { detailEvent(it) }
    }

    fun detailEvent(eventId: Int) {
        if (currentEventId == eventId && _eventData.value != null) {
            return
        }
        currentEventId = eventId
        _state.value = "LOADING"
        val client = ApiConfig.getApiService().getEventDetail(eventId)
        client.enqueue(object : Callback<EventResponseSingle> {
            override fun onResponse(call: Call<EventResponseSingle>, response: Response<EventResponseSingle>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (response.body()?.event == null) {
                            _state.value = "MESSAGE"
                            _message.value = "Sedang tidak ada detail Event"
                        } else {
                            _state.value = "CONTENT"
                            _eventData.value = response.body()?.event
                        }
                    }
                } else {
                    _state.value = "MESSAGE"
                    _message.value = "Event tidak ditemukan"
                    Log.e(TAG, "onResponse Failure: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<EventResponseSingle>, t: Throwable) {
                _state.value = "MESSAGE"
                _message.value = "Gagal memuat Event. Cek kembali koneksi anda"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}