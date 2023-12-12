package com.example.peladapp.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.peladapp.R
import com.example.peladapp.databinding.ActivitySignUpBinding
import androidx.lifecycle.ViewModelProvider

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.signUpButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.signUpButton -> {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                val confirmPassword = binding.repeatPasswordEditText.text.toString()
                val username = binding.nameEditText.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if (password == confirmPassword) {
                        viewModel.signUpUser(email, password, username) { success, message ->
                            if (success) {
                                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                                onBackPressed()
                            } else {
                                Toast.makeText(this, message ?: "Sign-up failed", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Password is not matching!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Missing information!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}