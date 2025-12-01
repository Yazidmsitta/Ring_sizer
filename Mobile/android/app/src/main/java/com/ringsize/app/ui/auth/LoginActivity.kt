package com.ringsize.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ringsize.app.R
import com.ringsize.app.databinding.ActivityLoginBinding
import com.ringsize.app.ui.MainActivity
import com.ringsize.app.ui.viewmodel.AuthViewModel
import com.ringsize.app.ui.viewmodel.AuthViewModelFactory
import com.ringsize.app.util.Result
import com.ringsize.app.util.SharedPreferencesManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val prefsManager = SharedPreferencesManager(this)
        val application = this.application as com.ringsize.app.RingSizerApplication
        viewModel = ViewModelProvider(this, AuthViewModelFactory(prefsManager, application))[AuthViewModel::class.java]
        
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                viewModel.register(name, email, password, password)
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (email.isNotEmpty()) {
                viewModel.forgotPassword(email)
            } else {
                Toast.makeText(this, "Veuillez entrer votre email", Toast.LENGTH_SHORT).show()
            }
        }
        
        viewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = android.view.View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    Toast.makeText(this, "Erreur: ${result.exception.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
        
        viewModel.registerResult.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = android.view.View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    Toast.makeText(this, "Erreur: ${result.exception.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}






