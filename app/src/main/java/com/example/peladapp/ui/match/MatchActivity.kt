package com.example.peladapp.ui.match

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peladapp.PlayerAdapter
import com.example.peladapp.R
import com.example.peladapp.databinding.ActivityMatchBinding
import com.example.peladapp.model.Match
import com.example.peladapp.model.Player
import com.example.peladapp.ui.matchroom.MatchRoomViewModel

class MatchActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMatchBinding
    private lateinit var chronometer: Chronometer
    private var isCounting = false
    private lateinit var viewModel: MatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MatchViewModel::class.java)

        val playerList: ArrayList<Player>? = intent.getSerializableExtra("PLAYER_LIST") as? ArrayList<Player>

        if (playerList != null) {
            viewModel.setPlayersList(playerList)
        }

        viewModel.playersList.observe(this) { playersList ->
            setupRecyclerView(binding.playersRecyclerView, playersList)
        }

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

    private fun setupRecyclerView(recyclerView: RecyclerView, playerList: List<Player>) {
        val adapter = PlayerAdapter(playerList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}