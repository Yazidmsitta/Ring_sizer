package com.ringsize.app.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ringsize.app.R
import com.ringsize.app.databinding.FragmentSettingsBinding
import com.ringsize.app.ui.MainActivity
import com.ringsize.app.ui.auth.LoginActivity
import com.ringsize.app.ui.viewmodel.AuthViewModel
import com.ringsize.app.ui.viewmodel.AuthViewModelFactory
import com.ringsize.app.util.SharedPreferencesManager
import com.ringsize.app.RingSizerApplication

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var authViewModel: AuthViewModel
    private lateinit var prefsManager: SharedPreferencesManager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        prefsManager = SharedPreferencesManager(requireContext())
        val application = requireActivity().application as RingSizerApplication
        authViewModel = ViewModelProvider(this, AuthViewModelFactory(prefsManager, application))[AuthViewModel::class.java]
        
        // Afficher les informations utilisateur
        val userName = prefsManager.getUserName()
        val userEmail = prefsManager.getUserEmail()
        
        binding.tvUserName.text = userName ?: "Utilisateur"
        binding.tvUserEmail.text = userEmail ?: ""
        
        binding.btnLogout.setOnClickListener {
            authViewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        
        authViewModel.logoutResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is com.ringsize.app.util.Result.Success -> {
                    Toast.makeText(context, "Déconnexion réussie", Toast.LENGTH_SHORT).show()
                }
                is com.ringsize.app.util.Result.Error -> {
                    Toast.makeText(context, "Erreur: ${result.exception.message}", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


