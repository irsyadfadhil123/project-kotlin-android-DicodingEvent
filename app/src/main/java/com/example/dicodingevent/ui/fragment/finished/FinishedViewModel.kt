package com.example.dicodingevent.ui.fragment.finished

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

class FinishedViewModel : ViewModel() {

    private val _eventItem = MutableLiveData<List<EventItem>>()
    val eventItem: LiveData<List<EventItem>> = _eventItem

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    companion object {
        private const val TAG = "FinishedViewModel"
    }

    init {
        finishedEvents()
    }

    fun retryFinishedEvents() {
        finishedEvents()
    }

    private fun finishedEvents() {
        _state.value = "LOADING"
        val client = ApiConfig.getApiService().getEvents("0", "40", "")
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _state.value = "CONTENT"
                        _eventItem.value = response.body()?.listEvents
                    } else {
                        _state.value = "MESSAGE"
                        _message.value = "Sedang tidak ada Event yang selesai"
                    }
                } else {
                    _state.value = "MESSAGE"
                    _message.value = "Event tidak ditemukan"
                    Log.e(TAG, "onResponse Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _state.value = "MESSAGE"
                _message.value = "Gagal memuat Event. Cek kembali koneksi anda"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}