package com.example.peladapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.peladapp.AppPreferences
import com.example.peladapp.ui.home.HomeActivity
import com.example.peladapp.R
import com.example.peladapp.ui.signup.SignUpActivity
import com.example.peladapp.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sp: AppPreferences
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = AppPreferences(this)

        val user = sp.getUser()

        if (user != null) {
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.loginResult.observe(this, Observer { result ->
            if (result) {
                val user = auth.currentUser
                user?.uid?.let { userId ->
                    AppPreferences.getInstance(getApplication()).setUser(userId)
                }
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        })

        binding.loginButton.setOnClickListener(this)
        binding.signUpTextView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loginButton -> {
                val email = binding.usernameEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.loginUser(email, password)
                } else {
                    Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.signUpTextView -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}