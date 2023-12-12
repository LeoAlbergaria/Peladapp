package com.example.peladapp.ui.match

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peladapp.PlayerAdapter
import com.example.peladapp.PlayerItemClickListener
import com.example.peladapp.R
import com.example.peladapp.databinding.ActivityMatchBinding
import com.example.peladapp.model.Player

class MatchActivity : AppCompatActivity(), View.OnClickListener, PlayerItemClickListener {

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
            setupRecyclerView(binding.playersRecyclerView, playersList, 1)
        }
        viewModel.team1Players.observe(this) { playersList ->
            setupRecyclerView(binding.team1RecyclerView, playersList, 2)
        }
        viewModel.team2Players.observe(this) { playersList ->
            setupRecyclerView(binding.team2RecyclerView, playersList, 3)
        }
        viewModel.team1Goals.observe(this) { gols ->
            binding.team1TitleTextView.text = String.format(getString(R.string.team1_title), gols)
        }
        viewModel.team2Goals.observe(this) { gols ->
            binding.team2TitleTextView.text = String.format(getString(R.string.team2_title), gols)
        }

        binding.playIcon.setOnClickListener(this)
        binding.stopIcon.setOnClickListener(this)
        binding.endMatchButton.setOnClickListener(this)

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
                viewModel.resetMatch()
                chronometer.base = SystemClock.elapsedRealtime();
                chronometer.stop();
            }
            R.id.endMatchButton -> {
                showConfirmationDialog()
            }
        }
    }

    private fun showTeamSelectionDialog(player: Player) {
        val teamOptions = arrayOf(getString(R.string.team_option_1), getString(R.string.team_option_2))

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_title))
            .setItems(teamOptions) { dialog, which ->
                when (which) {
                    0 -> viewModel.addPlayerToTeam1(player)
                    1 -> viewModel.addPlayerToTeam2(player)
                }
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure you want to end the match?")

        builder.setPositiveButton("Yes") { dialog, which ->
            finish()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }


    override fun onPlayerItemClick(player: Player, recyclerViewId: Int) {
        when (recyclerViewId) {
            1 -> {
                showTeamSelectionDialog(player)
            }
            2 -> {
                viewModel.addGoalToTeam1()
            }
            3 -> {
                viewModel.addGoalToTeam2()
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, playerList: List<Player>, recyclerViewId: Int) {
        val adapter = PlayerAdapter(playerList, this, recyclerViewId)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}