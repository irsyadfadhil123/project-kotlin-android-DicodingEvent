package com.example.dicodingevent.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.databinding.FragmentHomeBinding
import com.example.dicodingevent.ui.EventAdapter
import com.example.dicodingevent.ui.fragment.finished.FinishedViewModel
import com.example.dicodingevent.ui.fragment.upcoming.UpcomingViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val homeViewModel by viewModels<HomeViewModel>()
    private val upcomingViewModel by viewModels<UpcomingViewModel>()
    private val finishedViewModel by viewModels<FinishedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val upcomingLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val finishedLayoutManager = LinearLayoutManager(requireActivity())
        binding.rvHomeUpcoming.layoutManager = upcomingLayoutManager
        binding.rvHomeFinished.layoutManager = finishedLayoutManager
        binding.rvHomeFinished.isNestedScrollingEnabled = false

        with(binding) {

            svHomeEvent.setupWithSearchBar(sbHomeEvent)
        }

        upcomingViewModel.eventItem.observe(viewLifecycleOwner) { eventList ->
            setUpcomingEventData(eventList)
        }
        upcomingViewModel.state.observe(viewLifecycleOwner) {
            eventUpcomingState(it)
        }
        upcomingViewModel.message.observe(viewLifecycleOwner) {
            showUpcomingMessage(it)
        }


        finishedViewModel.eventItem.observe(viewLifecycleOwner) { eventList ->
            setFinishedEventData(eventList)
        }
        finishedViewModel.state.observe(viewLifecycleOwner) {
            eventFinishedState(it)
        }
        finishedViewModel.message.observe(viewLifecycleOwner) {
            showFinishedMessage(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpcomingEventData(eventList: List<EventItem>) {
        val upcomingAdapter = EventAdapter("logo")
        upcomingAdapter.submitList(eventList)
        binding.rvHomeUpcoming.adapter = upcomingAdapter
    }

    private fun setFinishedEventData(eventList: List<EventItem>) {
        val finishedAdapter = EventAdapter("cover")
        finishedAdapter.submitList(eventList)
        binding.rvHomeFinished.adapter = finishedAdapter
    }

    private fun eventUpcomingState(isLoading: String) {
        when (isLoading) {
            "LOADING" -> {
                binding.rvHomeUpcoming.visibility = View.GONE
                binding.pbHomeUpcoming.visibility = View.VISIBLE
                binding.tvHomeUpcomingMessage.visibility = View.GONE
                binding.btnHomeUpcomingRetry.visibility = View.GONE
            }
            "CONTENT" -> {
                binding.rvHomeUpcoming.visibility = View.VISIBLE
                binding.pbHomeUpcoming.visibility = View.GONE
                binding.tvHomeUpcomingMessage.visibility = View.GONE
                binding.btnHomeUpcomingRetry.visibility = View.GONE
            }
            else -> {
                binding.rvHomeUpcoming.visibility = View.GONE
                binding.pbHomeUpcoming.visibility = View.GONE
                binding.tvHomeUpcomingMessage.visibility = View.VISIBLE
                binding.btnHomeUpcomingRetry.visibility = View.VISIBLE
            }
        }
    }

    private fun showUpcomingMessage(message: String) {
        binding.tvHomeUpcomingMessage.text = message
    }

    private fun eventFinishedState(isLoading: String) {
        when (isLoading) {
            "LOADING" -> {
                binding.rvHomeFinished.visibility = View.GONE
                binding.pbHomeFinished.visibility = View.VISIBLE
                binding.tvHomeFinishedMessage.visibility = View.GONE
                binding.btnHomeFinishedRetry.visibility = View.GONE
            }
            "CONTENT" -> {
                binding.rvHomeFinished.visibility = View.VISIBLE
                binding.pbHomeFinished.visibility = View.GONE
                binding.tvHomeFinishedMessage.visibility = View.GONE
                binding.btnHomeFinishedRetry.visibility = View.GONE
            }
            else -> {
                binding.rvHomeFinished.visibility = View.GONE
                binding.pbHomeFinished.visibility = View.GONE
                binding.tvHomeFinishedMessage.visibility = View.VISIBLE
                binding.btnHomeFinishedRetry.visibility = View.VISIBLE
            }
        }
    }

    private fun showFinishedMessage(message: String) {
        binding.tvHomeFinishedMessage.text = message
    }
}