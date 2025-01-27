package com.example.dicodingevent.ui.fragment.home

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.databinding.FragmentHomeBinding
import com.example.dicodingevent.ui.EventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val homeUpcomingViewModel by viewModels<HomeUpcomingViewModel>()
    private val homeFinishedViewModel by viewModels<HomeFinishedViewModel>()
    private val homeSearchViewModel by viewModels<HomeSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with(binding) {
            val upcomingLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            val finishedLayoutManager = LinearLayoutManager(requireActivity())
            val searchViewModel = GridLayoutManager(requireActivity(), 2)
            rvHomeUpcoming.layoutManager = upcomingLayoutManager
            rvHomeFinished.layoutManager = finishedLayoutManager
            rvHomeFinished.isNestedScrollingEnabled = false
            rvHomeSearch.layoutManager = searchViewModel

            svHomeEvent.setupWithSearchBar(sbHomeEvent)
            svHomeEvent.editText.addTextChangedListener(object : android.text.TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val query = p0.toString()
                    homeSearchViewModel.homeSearchEvents(query)
                }

                override fun afterTextChanged(p0: Editable?) {}

            })
        }

        homeUpcomingViewModel.eventItem.observe(viewLifecycleOwner) { eventList ->
            setUpcomingEventData(eventList)
        }
        homeUpcomingViewModel.state.observe(viewLifecycleOwner) {
            eventUpcomingState(it)
        }
        homeUpcomingViewModel.message.observe(viewLifecycleOwner) {
            showUpcomingMessage(it)
        }


        homeFinishedViewModel.eventItem.observe(viewLifecycleOwner) { eventList ->
            setFinishedEventData(eventList)
        }
        homeFinishedViewModel.state.observe(viewLifecycleOwner) {
            eventFinishedState(it)
        }
        homeFinishedViewModel.message.observe(viewLifecycleOwner) {
            showFinishedMessage(it)
        }


        homeSearchViewModel.eventItem.observe(viewLifecycleOwner) { eventList ->
            setSearchEventData(eventList)
        }
        homeSearchViewModel.state.observe(viewLifecycleOwner) {
            eventSearchState(it)
        }
        homeSearchViewModel.message.observe(viewLifecycleOwner) {
            showSearchMessage(it)
        }


        binding.btnHomeUpcomingRetry.setOnClickListener {
            homeUpcomingViewModel.retryHomeUpcomingEvents()
        }
        binding.btnHomeFinishedRetry.setOnClickListener {
            homeFinishedViewModel.retryHomeFinishedEvents()
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

    private fun setSearchEventData(eventList: List<EventItem>) {
        val searchAdapter = EventAdapter("logo")
        searchAdapter.submitList(eventList)
        binding.rvHomeSearch.adapter = searchAdapter
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

    private fun eventSearchState(isLoading: String) {
        when (isLoading) {
            "LOADING" -> {
                binding.rvHomeSearch.visibility = View.GONE
                binding.pbHomeSearch.visibility = View.VISIBLE
                binding.tvHomeSearchMessage.visibility = View.GONE
            }
            "CONTENT" -> {
                binding.rvHomeSearch.visibility = View.VISIBLE
                binding.pbHomeSearch.visibility = View.GONE
                binding.tvHomeSearchMessage.visibility = View.GONE
            }
            else -> {
                binding.rvHomeSearch.visibility = View.GONE
                binding.pbHomeSearch.visibility = View.GONE
                binding.tvHomeSearchMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun showSearchMessage(message: String) {
        binding.tvHomeSearchMessage.text = message
    }
}