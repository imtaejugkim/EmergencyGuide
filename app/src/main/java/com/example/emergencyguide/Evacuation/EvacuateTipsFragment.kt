package com.example.emergencyguide.Evacuation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emergencyguide.R
import com.example.emergencyguide.databinding.FragmentEvacuateTipsBinding

class EvacuateTipsFragment() : Fragment() {
    lateinit var binding: FragmentEvacuateTipsBinding
    private lateinit var evacuateTipsAdapter: EvacuateTipsAdapter
    private var evacuateTipsData: ArrayList<EvacuateTipsData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEvacuateTipsBinding.inflate(layoutInflater)

        setRecyclerView()

        return binding.root
    }

    private fun setRecyclerView() {
        evacuateTipsAdapter = EvacuateTipsAdapter(requireContext(), evacuateTipsData)
        binding.rvEvaTips.adapter = evacuateTipsAdapter
        binding.rvEvaTips.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
}