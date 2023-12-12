package com.example.peladapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.peladapp.ui.preparematch.PrepareMatchFragment
import com.example.peladapp.R
import com.example.peladapp.ui.user.UserFragment
import com.example.peladapp.databinding.ActivityHomeBinding

/**
 * Activity representing the home screen of the application.
 *
 * This activity displays a bottom navigation bar with options to navigate to different fragments.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down, then this Bundle contains the data it most recently supplied in
     * onSaveInstanceState(Bundle). Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Display the initial fragment
        replaceFragment(PrepareMatchFragment())

        // Set up bottom navigation item selection listener
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.match -> replaceFragment(PrepareMatchFragment())
                R.id.user -> replaceFragment(UserFragment())
                else -> {
                    // Handle other cases if needed
                }
            }
            true
        }

        // Customize bottom navigation bar appearance
        val bottomNavigationBar = binding.bottomNavigationView
        val defaultIconColor = ContextCompat.getColorStateList(this, R.color.white)
        bottomNavigationBar.itemIconTintList = defaultIconColor
        bottomNavigationBar.itemTextColor = defaultIconColor
    }

    /**
     * Replaces the current fragment with the specified [fragment].
     *
     * @param fragment The fragment to replace the current one.
     */
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}
