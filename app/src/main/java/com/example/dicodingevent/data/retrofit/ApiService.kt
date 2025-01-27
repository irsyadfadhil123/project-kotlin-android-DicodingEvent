package com.example.dicodingevent.data.retrofit

import com.example.dicodingevent.data.response.EventResponse
import com.example.dicodingevent.data.response.EventResponseSingle
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("events")
    fun getEvents(
        @Query("active") active: String,
        @Query("limit") limit: String,
        @Query("q") q: String
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getEventDetail(
        @Path("id") id: Int
    ): Call<EventResponseSingle>
}