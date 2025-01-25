package com.example.dicodingevent.ui.fragment.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dicodingevent.data.response.EventItem
import com.example.dicodingevent.databinding.FragmentUnfinishedAndFinishedBinding
import com.example.dicodingevent.ui.EventAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentUnfinishedAndFinishedBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val finishedViewModel =
            ViewModelProvider(this)[FinishedViewModel::class.java]

        _binding = FragmentUnfinishedAndFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvEventList.layoutManager = layoutManager

        finishedViewModel.eventItem.observe(viewLifecycleOwner) { eventList ->
            setEventData(eventList)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFinished.visibility = View.VISIBLE
            binding.tvMessage.visibility = View.INVISIBLE
        } else {
            binding.pbFinished.visibility = View.INVISIBLE
        }
    }

    private fun showMessage(message: String) {
        if (message.isEmpty()) {
            binding.tvMessage.visibility = View.INVISIBLE
        } else {
            binding.tvMessage.visibility = View.VISIBLE
            binding.tvMessage.text = message
        }
    }

}