package com.example.peladapp.ui.match

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import com.example.peladapp.R
import com.example.peladapp.databinding.ActivityMatchBinding

class MatchActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMatchBinding
    private lateinit var chronometer: Chronometer
    private var isCounting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playIcon.setOnClickListener(this)
        binding.stopIcon.setOnClickListener(this)

        chronometer = binding.chronometer
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.playIcon -> {
                isCounting = if(isCounting){
                    chronometer.stop()
                    false
                } else {
                    chronometer.start()
                    true
                }
            }
            R.id.stopIcon -> {
                chronometer.base = SystemClock.elapsedRealtime();
                chronometer.stop();
            }
        }
    }
}