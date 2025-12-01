package com.ringsize.app.ui.marketplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ringsize.app.R
import com.ringsize.app.databinding.FragmentMarketplaceBinding
import com.ringsize.app.ui.viewmodel.ProductViewModel
import com.ringsize.app.ui.viewmodel.ProductViewModelFactory
import com.ringsize.app.util.Result
import com.ringsize.app.util.SharedPreferencesManager

class MarketplaceFragment : Fragment() {
    private var _binding: FragmentMarketplaceBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketplaceBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val prefsManager = SharedPreferencesManager(requireContext())
        viewModel = ViewModelProvider(this, ProductViewModelFactory(prefsManager))[ProductViewModel::class.java]
        
        adapter = ProductAdapter { product ->
            showProductDetails(product)
        }
        
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        
        viewModel.products.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    if (result.data.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    } else {
                        binding.tvEmpty.visibility = View.GONE
                        adapter.submitList(result.data)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    Toast.makeText(context, "Erreur: ${result.exception.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
        
        viewModel.loadProducts()
    }
    
    private fun showProductDetails(product: com.ringsize.app.data.remote.model.ProductResponse) {
        val message = """
            ${product.name}
            Prix: ${product.price} ${product.currency}
            Vendeur: ${product.vendor?.shopName ?: "Inconnu"}
            ${product.description ?: ""}
        """.trimIndent()
        
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Détails du produit")
            .setMessage(message)
            .setPositiveButton("Contacter le vendeur") { _, _ ->
                if (!product.vendor?.websiteUrl.isNullOrEmpty()) {
                    // Ouvrir le site web du vendeur
                    Toast.makeText(context, "Ouverture du site web...", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Aucun site web disponible", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Fermer", null)
            .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


