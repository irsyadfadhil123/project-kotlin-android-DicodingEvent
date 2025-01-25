package com.example.dicodingevent.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.databinding.ActivityEventDetailBinding

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding

    private val eventDetailViewModel by viewModels<EventDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val eventId = intent.getStringExtra("ID")?.toInt()
        if (eventId != null) {
            eventDetailViewModel.detailEvent(eventId)
        }

        eventDetailViewModel.eventData.observe(this) {
            setEventData(it)
        }

        eventDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        eventDetailViewModel.message.observe(this) {
            showMessage(it)
        }
    }

    private fun setEventData(data: EventItem?) {
        Glide.with(this)
            .load(data?.mediaCover)
            .into(binding.ivEventImage)
        binding.tvEventCategory.text = data?.category
        binding.tvEventName.text = data?.name
        binding.tvEventOwner.text = data?.ownerName
        binding.tvEventDescription.text = HtmlCompat.fromHtml(
            data?.description.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.tvEventBeginTime.text = data?.beginTime
        binding.tvEventEndTime.text = data?.endTime
        binding.tvEventCityName.text = data?.cityName
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbDetailLoading.visibility = View.VISIBLE
            binding.tvDetailMessage.visibility = View.INVISIBLE
        } else {
            binding.pbDetailLoading.visibility = View.INVISIBLE
        }
    }

    private fun showMessage(message: String) {
        if (message.isEmpty()) {
            binding.tvDetailMessage.visibility = View.INVISIBLE
        } else {
            binding.tvDetailMessage.visibility = View.VISIBLE
            binding.tvDetailMessage.text = message
        }
    }

}