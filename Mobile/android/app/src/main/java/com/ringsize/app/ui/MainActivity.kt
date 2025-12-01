package com.ringsize.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ringsize.app.R
import com.ringsize.app.databinding.ActivityMainBinding
import com.ringsize.app.ui.auth.LoginActivity
import com.ringsize.app.util.SharedPreferencesManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Vérifier si l'utilisateur est connecté
        val prefsManager = SharedPreferencesManager(this)
        if (prefsManager.getAuthToken().isNullOrEmpty()) {
            // Rediriger vers la page de connexion
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        
        // Initialiser l'interface principale
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Configurer la navigation
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}


