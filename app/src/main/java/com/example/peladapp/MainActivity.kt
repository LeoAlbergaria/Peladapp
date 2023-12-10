package com.example.peladapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.peladapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sp: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        sp = AppPreferences(this)

        val user = sp.getUser()

        if(user != null) {Toast.makeText(this , "Welcome back!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener(this)
        binding.signUpTextView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loginButton -> {
                val email = binding.usernameEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString()

                if(email.isNotEmpty() && password.isNotEmpty()){
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) {
                            if (it.isSuccessful) {
                                val user = auth.currentUser
                                user?.uid?.let { userId ->
                                    sp.setUser(userId)
                                    Toast.makeText(this , "Welcome $email", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, HomeActivity::class.java)
                                    startActivity(intent)
                                }
                            } else {
                                Toast.makeText(this , it.exception.toString(), Toast.LENGTH_LONG).show()
                                println(it.exception.toString())
                                Log.d("text", it.exception.toString())
                            }
                        }
                } else {
                    Toast.makeText(this , "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.signUpTextView -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}