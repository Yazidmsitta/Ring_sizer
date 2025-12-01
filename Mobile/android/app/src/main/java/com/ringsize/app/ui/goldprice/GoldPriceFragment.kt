package com.ringsize.app.ui.goldprice

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.ringsize.app.R
import com.ringsize.app.databinding.FragmentGoldPriceBinding
import com.ringsize.app.ui.viewmodel.GoldPriceViewModel
import com.ringsize.app.ui.viewmodel.GoldPriceViewModelFactory
import com.ringsize.app.util.Result
import com.ringsize.app.util.SharedPreferencesManager
import java.text.SimpleDateFormat
import java.util.*

class GoldPriceFragment : Fragment() {
    private var _binding: FragmentGoldPriceBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: GoldPriceViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoldPriceBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val prefsManager = SharedPreferencesManager(requireContext())
        viewModel = ViewModelProvider(this, GoldPriceViewModelFactory(prefsManager))[GoldPriceViewModel::class.java]
        
        setupChart()
        
        binding.btnDay.setOnClickListener {
            viewModel.setPeriod("day")
        }
        
        binding.btnWeek.setOnClickListener {
            viewModel.setPeriod("week")
        }
        
        binding.btnMonth.setOnClickListener {
            viewModel.setPeriod("month")
        }
        
        binding.btnYear.setOnClickListener {
            viewModel.setPeriod("year")
        }
        
        viewModel.goldPrices.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.chartGoldPrice.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.chartGoldPrice.visibility = View.VISIBLE
                    updateChart(result.data)
                }
                is Result.Error -> {
                    binding.chartGoldPrice.visibility = View.GONE
                }
            }
        }
        
        viewModel.loadGoldPrices()
    }
    
    private fun setupChart() {
        val chart = binding.chartGoldPrice
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.setDragEnabled(true)
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)
        
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK
        xAxis.setDrawGridLines(false)
        
        val leftAxis = chart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.setDrawGridLines(true)
        
        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false
    }
    
    private fun updateChart(prices: List<com.ringsize.app.data.remote.model.GoldPriceResponse>) {
        if (prices.isEmpty()) {
            return
        }
        
        val entries = prices.mapIndexed { index, price ->
            Entry(index.toFloat(), price.pricePerGram.toFloat())
        }
        
        val dataSet = LineDataSet(entries, "Prix de l'or (€/g)")
        dataSet.color = Color.parseColor("#FFD700")
        dataSet.valueTextColor = Color.BLACK
        dataSet.lineWidth = 2f
        dataSet.setCircleColor(Color.parseColor("#DAA520"))
        dataSet.circleRadius = 4f
        dataSet.setDrawValues(false)
        
        val lineData = LineData(dataSet)
        binding.chartGoldPrice.data = lineData
        
        val xAxis = binding.chartGoldPrice.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                if (index >= 0 && index < prices.size) {
                    val dateStr = prices[index].dateRecorded
                    return try {
                        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
                        val date = inputFormat.parse(dateStr)
                        outputFormat.format(date ?: Date())
                    } catch (e: Exception) {
                        dateStr
                    }
                }
                return ""
            }
        }
        
        binding.chartGoldPrice.invalidate()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


