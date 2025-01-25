package com.example.dicodingevent.ui.fragment.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.databinding.FragmentFinishedBinding
import com.example.dicodingevent.ui.EventAdapter

class UpcomingFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val upcomingViewModel =
            ViewModelProvider(this)[UpcomingViewModel::class.java]

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvEventList.layoutManager = layoutManager

        upcomingViewModel.eventItem.observe(viewLifecycleOwner) { eventList ->
            setEventData(eventList)
        }

        upcomingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setEventData(eventList: List<EventItem>) {
        val adapter = EventAdapter("logo")
        adapter.submitList(eventList)
        binding.rvEventList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.finishedProgressBar.visibility = View.VISIBLE
        } else {
            binding.finishedProgressBar.visibility = View.INVISIBLE
        }
    }
}