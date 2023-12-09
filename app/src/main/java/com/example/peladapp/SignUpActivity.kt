package com.example.peladapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.peladapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

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

                if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if(password == confirmPassword){
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                            if(it.isSuccessful) {
                                Toast.makeText(this , "Success!", Toast.LENGTH_SHORT).show()
                                onBackPressed()
                            } else {
                                Toast.makeText(this , it.exception.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(this , "Password is not matching!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this , "Missing information!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}