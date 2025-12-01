package com.ringsize.app.ui.marketplace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ringsize.app.R
import com.ringsize.app.data.remote.model.ProductResponse

class ProductAdapter(
    private val onItemClick: (ProductResponse) -> Unit
) : ListAdapter<ProductResponse, ProductAdapter.ViewHolder>(DiffCallback()) {
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProductImage: ImageView = view.findViewById(R.id.ivProductImage)
        val tvProductName: TextView = view.findViewById(R.id.tvProductName)
        val tvVendorName: TextView = view.findViewById(R.id.tvVendorName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.tvProductName.text = product.name
        holder.tvVendorName.text = product.vendor?.shopName ?: "Vendeur inconnu"
        holder.tvPrice.text = "${product.price} ${product.currency}"
        
        // Charger l'image avec Glide
        if (!product.mainImageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(product.mainImageUrl)
                .placeholder(R.color.light_gray)
                .into(holder.ivProductImage)
        }
        
        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }
    
    class DiffCallback : DiffUtil.ItemCallback<ProductResponse>() {
        override fun areItemsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return oldItem == newItem
        }
    }
}






