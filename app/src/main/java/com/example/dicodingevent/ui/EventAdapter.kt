package com.example.dicodingevent.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.databinding.ItemEventBinding

class EventAdapter(
    private val imageType: String
) : ListAdapter<EventItem, EventAdapter.MyViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, imageType)
    }

    class MyViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventItem, imageType: String) {
            val imageUrl = when (imageType) {
                "logo" -> event.imageLogo
                else -> event.mediaCover
            }
            Glide.with(binding.ivEventImg.context)
                .load(imageUrl)
                .into(binding.ivEventImg)
            binding.tvEventTitle.text ="${event.name}"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventItem>() {
            override fun areItemsTheSame(
                oldItem: EventItem,
                newItem: EventItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: EventItem,
                newItem: EventItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}