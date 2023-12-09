package com.example.peladapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.peladapp.databinding.ActivityPrepareMatchBinding

class PrepareMatchActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPrepareMatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrepareMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.createMatchButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.createMatchButton -> {
                val intent = Intent(this, MatchRoomActivity::class.java)
                startActivity(intent)
            }
        }
    }
}