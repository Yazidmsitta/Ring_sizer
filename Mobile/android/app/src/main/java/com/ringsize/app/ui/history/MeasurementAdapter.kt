package com.ringsize.app.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ringsize.app.R
import com.ringsize.app.data.local.entity.MeasurementEntity

class MeasurementAdapter(
    private val onItemClick: (MeasurementEntity) -> Unit
) : ListAdapter<MeasurementEntity, MeasurementAdapter.ViewHolder>(DiffCallback()) {
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvType: TextView = view.findViewById(R.id.tvType)
        val tvSize: TextView = view.findViewById(R.id.tvSize)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_measurement, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val measurement = getItem(position)
        holder.tvName.text = measurement.name
        holder.tvType.text = measurement.type.name
        holder.tvSize.text = "EU: ${measurement.sizeEu?.let { String.format("%.1f", it) } ?: "--"}, US: ${measurement.sizeUs?.let { String.format("%.1f", it) } ?: "--"}"
        
        holder.itemView.setOnClickListener {
            onItemClick(measurement)
        }
    }
    
    class DiffCallback : DiffUtil.ItemCallback<MeasurementEntity>() {
        override fun areItemsTheSame(oldItem: MeasurementEntity, newItem: MeasurementEntity): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: MeasurementEntity, newItem: MeasurementEntity): Boolean {
            return oldItem == newItem
        }
    }
}







