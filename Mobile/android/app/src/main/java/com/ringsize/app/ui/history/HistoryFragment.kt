package com.ringsize.app.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ringsize.app.R
import com.ringsize.app.databinding.FragmentHistoryBinding
import com.ringsize.app.ui.viewmodel.MeasurementViewModel
import com.ringsize.app.ui.viewmodel.MeasurementViewModelFactory
import com.ringsize.app.util.SharedPreferencesManager
import com.ringsize.app.RingSizerApplication

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: MeasurementViewModel
    private lateinit var adapter: MeasurementAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val prefsManager = SharedPreferencesManager(requireContext())
        val application = requireActivity().application as RingSizerApplication
        viewModel = ViewModelProvider(this, MeasurementViewModelFactory(prefsManager, application))[MeasurementViewModel::class.java]
        
        adapter = MeasurementAdapter { measurement ->
            // Handle item click - show edit/delete dialog
        }
        
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        
        viewModel.measurements.observe(viewLifecycleOwner) { measurements ->
            if (measurements.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(measurements)
            }
        }
        
        binding.btnSync.setOnClickListener {
            viewModel.syncMeasurements()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}







