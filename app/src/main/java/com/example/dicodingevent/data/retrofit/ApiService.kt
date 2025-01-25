package com.example.dicodingevent.data.retrofit

import com.example.dicodingevent.data.response.EventResponse
import com.example.dicodingevent.data.response.EventItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events?active=0")
    fun getFinishedEvent(

    ): Call<EventResponse>

    @GET("events?active=1")
    fun getUpcomingEvent(

    ):Call<EventResponse>

    @GET("events/{id}")
    fun getEventDetail(
        @Path("id") id: String
    ): Call<EventItem>
}