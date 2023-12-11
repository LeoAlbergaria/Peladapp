package com.example.peladapp.ui.matchroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.peladapp.ui.match.MatchActivity
import com.example.peladapp.R
import com.example.peladapp.databinding.ActivityMatchRoomBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peladapp.PlayerAdapter
import com.example.peladapp.model.Match
import com.example.peladapp.model.Player

class MatchRoomActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMatchRoomBinding
    private lateinit var viewModel: MatchRoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        viewModel = ViewModelProvider(this).get(MatchRoomViewModel::class.java)

        val match: Match? = intent.getSerializableExtra("MATCH") as? Match

        if (match != null) {
            viewModel.fetchData(match)
        }

        viewModel.selectedMatch.observe(this) { match ->
            match?.let {
                binding.codeText.text = it.code
            }
        }

        viewModel.confirmedList.observe(this) { players ->
            setupRecyclerView(binding.confirmedRecyclerView, players)
        }

        viewModel.waitingList.observe(this) { players ->
            setupRecyclerView(binding.waitingRecyclerView, players)
        }

        viewModel.outList.observe(this) { players ->
            setupRecyclerView(binding.outRecyclerView, players)
        }

        binding.startMatchButton.setOnClickListener(this)
        binding.confirmButton.setOnClickListener(this)
        binding.outButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.startMatchButton -> {
                val intent = Intent(this, MatchActivity::class.java)
                startActivity(intent)
            }
            R.id.confirmButton -> {
                viewModel.updatePlayer(2)
            }
            R.id.outButton -> {
                viewModel.updatePlayer(3)
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, playerList: List<Player>) {
        val adapter = PlayerAdapter(playerList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
