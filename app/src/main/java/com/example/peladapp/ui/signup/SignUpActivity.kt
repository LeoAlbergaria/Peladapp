package com.example.peladapp.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.peladapp.R
import com.example.peladapp.databinding.ActivitySignUpBinding
import androidx.lifecycle.ViewModelProvider

/**
 * Activity for user signup.
 *
 * This activity allows users to sign up by providing their email, password, and username.
 */
class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down, then this Bundle contains the data it most recently supplied in
     * onSaveInstanceState(Bundle). Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set up navigation icon click listener
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Set up click listener for the sign-up button
        binding.signUpButton.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.signUpButton -> {
                // Handle click on the sign-up button
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                val confirmPassword = binding.repeatPasswordEditText.text.toString()
                val username = binding.nameEditText.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if (password == confirmPassword) {
                        // Attempt to sign up the user with the provided information
                        viewModel.signUpUser(email, password, username) { success, message ->
                            if (success) {
                                // Display success message and go back to the previous screen
                                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                                onBackPressed()
                            } else {
                                // Display an error message if sign-up fails
                                Toast.makeText(this, message ?: "Sign-up failed", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        // Display a message for password mismatch
                        Toast.makeText(this, "Password is not matching!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Display a message for missing information
                    Toast.makeText(this, "Missing information!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
