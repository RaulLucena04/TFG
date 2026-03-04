package com.tfg.nbapredictor.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.databinding.ActivityRegisterBinding
import com.tfg.nbapredictor.model.User
import com.tfg.nbapredictor.network.RetrofitClient
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegisterBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupListeners()
    }
    
    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            performRegister()
        }
        
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
    
    private fun performRegister() {
        val username = binding.etUsername.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        
        // Validaciones
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.all_fields_required, Toast.LENGTH_SHORT).show()
            return
        }
        
        if (password.length < 6) {
            Toast.makeText(this, R.string.password_min_length, Toast.LENGTH_SHORT).show()
            return
        }
        
        if (password != confirmPassword) {
            Toast.makeText(this, R.string.passwords_not_match, Toast.LENGTH_SHORT).show()
            return
        }
        
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.btnRegister.isEnabled = false
        
        lifecycleScope.launch {
            try {
                val user = User(username = username, email = email, password = password)
                val response = RetrofitClient.apiService.register(user)
                
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(
                        this@RegisterActivity,
                        getString(R.string.success),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() 
                        ?: getString(R.string.register_error)
                    Toast.makeText(
                        this@RegisterActivity,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@RegisterActivity,
                    getString(R.string.register_error) + ": ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
                binding.btnRegister.isEnabled = true
            }
        }
    }
}
