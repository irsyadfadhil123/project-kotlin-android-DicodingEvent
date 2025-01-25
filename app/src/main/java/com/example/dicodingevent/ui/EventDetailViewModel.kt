package com.example.dicodingevent.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.response.EventItem

class EventDetailViewModel : ViewModel() {
    private val _eventItem = MutableLiveData<List<EventItem>>()
    val eventItem: LiveData<List<EventItem>> = _eventItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "EventDetailViewModel"
    }
}