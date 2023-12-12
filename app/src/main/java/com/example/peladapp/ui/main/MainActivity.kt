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

/**
 * Main activity for user authentication and login.
 *
 * This activity provides a login form for users to enter their credentials.
 * It also handles authentication and navigation to the home screen upon successful login.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sp: AppPreferences
    private val auth = FirebaseAuth.getInstance()

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down, then this Bundle contains the data it most recently supplied in
     * onSaveInstanceState(Bundle). Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = AppPreferences(this)

        // Check if the user is already logged in
        val user = sp.getUser()
        if (user != null) {
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Observe login result
        viewModel.loginResult.observe(this, Observer { result ->
            if (result) {
                // Save user ID to preferences upon successful login
                val user = auth.currentUser
                user?.uid?.let { userId ->
                    AppPreferences.getInstance(application).setUser(userId)
                }
                // Navigate to the home screen
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Display login failure message
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        })

        // Set up click listeners
        binding.loginButton.setOnClickListener(this)
        binding.signUpTextView.setOnClickListener(this)
    }

    /**
     * Handles clicks on the views.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.loginButton -> {
                // Attempt to login when the login button is clicked
                val email = binding.usernameEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.loginUser(email, password)
                } else {
                    // Display a message for empty fields
                    Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.signUpTextView -> {
                // Navigate to the sign-up screen when the sign-up text view is clicked
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
