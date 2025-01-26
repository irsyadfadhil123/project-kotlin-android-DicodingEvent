package com.example.dicodingevent.ui.fragment.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.databinding.FragmentUnfinishedAndFinishedBinding
import com.example.dicodingevent.ui.EventAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentUnfinishedAndFinishedBinding? = null
    private val binding get() = _binding!!

    private val finishedViewModel by viewModels<FinishedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUnfinishedAndFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvEventList.layoutManager = layoutManager

        finishedViewModel.eventItem.observe(viewLifecycleOwner) { eventList ->
            setEventData(eventList)
        }
        finishedViewModel.state.observe(viewLifecycleOwner) {
            eventState(it)
        }
        finishedViewModel.message.observe(viewLifecycleOwner) {
            showMessage(it)
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

    private fun eventState(isLoading: String) {
        when (isLoading) {
            "LOADING" -> {
                binding.rvEventList.visibility = View.GONE
                binding.pbFinished.visibility = View.VISIBLE
                binding.tvMessage.visibility = View.GONE
                binding.btnRetry.visibility = View.GONE
            }
            "CONTENT" -> {
                binding.rvEventList.visibility = View.VISIBLE
                binding.pbFinished.visibility = View.GONE
                binding.tvMessage.visibility = View.GONE
                binding.btnRetry.visibility = View.GONE
            }
            else -> {
                binding.rvEventList.visibility = View.GONE
                binding.pbFinished.visibility = View.GONE
                binding.tvMessage.visibility = View.VISIBLE
                binding.btnRetry.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        binding.tvMessage.text = message
    }
}