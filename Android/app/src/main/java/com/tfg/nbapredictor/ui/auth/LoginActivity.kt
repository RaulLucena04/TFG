package com.tfg.nbapredictor.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.databinding.ActivityLoginBinding
import com.tfg.nbapredictor.model.User
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.ui.main.MainActivity
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupListeners()
    }
    
    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            performLogin()
        }
        
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    
    private fun performLogin() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.all_fields_required, Toast.LENGTH_SHORT).show()
            return
        }
        
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.btnLogin.isEnabled = false
        
        lifecycleScope.launch {
            try {
                val user = User(username = username, password = password)
                val response = RetrofitClient.apiService.login(user)
                
                if (response.isSuccessful && response.body() != null) {
                    val loggedUser = response.body()!!
                    Session.setCurrentUser(loggedUser)
                    
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        response.message() ?: getString(R.string.login_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.login_error) + ": ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
                binding.btnLogin.isEnabled = true
            }
        }
    }
}
