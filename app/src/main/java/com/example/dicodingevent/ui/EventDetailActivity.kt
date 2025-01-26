package com.example.dicodingevent.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
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
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        val eventId = intent.getStringExtra("ID")?.toInt()
        if (eventId != null) {
            eventDetailViewModel.detailEvent(eventId)
        }

        eventDetailViewModel.eventData.observe(this) {
            setEventData(it)
        }

        eventDetailViewModel.state.observe(this) {
            eventState(it)
        }

        eventDetailViewModel.message.observe(this) {
            showMessage(it)
        }

        binding.btnRetryEventDetail.setOnClickListener {
            eventDetailViewModel.retryDetailEvent()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setEventData(data: EventItem?) {
        supportActionBar?.title = data?.name
        Glide.with(this)
            .load(data?.mediaCover)
            .into(binding.ivEventImage)
        binding.tvEventCategory.text = data?.category
        binding.tvEventName.text = data?.name
        binding.tvEventOwner.text = data?.ownerName
        binding.tvEventQuota.text = data?.quota.toString()
        binding.tvEventDescription.text = HtmlCompat.fromHtml(
            data?.description.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.tvEventBeginTime.text = data?.beginTime
        binding.tvEventEndTime.text = data?.endTime
        binding.tvEventCityName.text = data?.cityName
        binding.btnRegister.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(data?.link)
            startActivity(intent)
        }
    }

    private fun eventState(isLoading: String) {
        when (isLoading) {
            "LOADING" -> {
                binding.slEventDetail.visibility = View.GONE
                binding.pbDetailLoading.visibility = View.VISIBLE
                binding.tvDetailMessage.visibility = View.GONE
                binding.btnRetryEventDetail.visibility = View.GONE
            }
            "CONTENT" -> {
                binding.slEventDetail.visibility = View.VISIBLE
                binding.pbDetailLoading.visibility = View.GONE
                binding.tvDetailMessage.visibility = View.GONE
                binding.btnRetryEventDetail.visibility = View.GONE
            }
            else -> {
                binding.slEventDetail.visibility = View.GONE
                binding.pbDetailLoading.visibility = View.GONE
                binding.tvDetailMessage.visibility = View.VISIBLE
                binding.btnRetryEventDetail.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        binding.tvDetailMessage.text = message
    }
}