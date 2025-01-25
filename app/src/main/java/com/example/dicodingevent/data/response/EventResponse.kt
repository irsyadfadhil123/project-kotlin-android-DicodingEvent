package com.example.dicodingevent.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class EventResponse(

	@field:SerializedName("listEvents")
	val listEvents: List<EventItem> = listOf(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class EventResponseSingle(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("event")
	val event: EventItem? = null
)


@Parcelize
data class EventItem(

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("mediaCover")
	val mediaCover: String? = null,

	@field:SerializedName("registrants")
	val registrants: Int? = null,

	@field:SerializedName("imageLogo")
	val imageLogo: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ownerName")
	val ownerName: String? = null,

	@field:SerializedName("cityName")
	val cityName: String? = null,

	@field:SerializedName("quota")
	val quota: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("beginTime")
	val beginTime: String? = null,

	@field:SerializedName("endTime")
	val endTime: String? = null,

	@field:SerializedName("category")
	val category: String? = null
) : Parcelable
