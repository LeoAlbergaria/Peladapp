package com.example.peladapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.peladapp.databinding.ActivityHomeBinding
import com.example.peladapp.databinding.ActivityPrepareMatchBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(PrepareMatchFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.match -> replaceFragment(PrepareMatchFragment())
                R.id.user -> replaceFragment(UserFragment())
                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}