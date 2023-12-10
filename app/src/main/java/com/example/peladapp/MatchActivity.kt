package com.example.peladapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.peladapp.databinding.ActivityMatchBinding

class MatchActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
        }
    }
}