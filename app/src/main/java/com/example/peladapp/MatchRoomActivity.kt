package com.example.peladapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.peladapp.databinding.ActivityMatchRoomBinding

class MatchRoomActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMatchRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Setting up click listener for startMatch button
        binding.startMatchButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.startMatchButton -> {
                val intent = Intent(this, MatchActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
