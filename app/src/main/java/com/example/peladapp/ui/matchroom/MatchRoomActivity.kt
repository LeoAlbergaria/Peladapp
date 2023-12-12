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

/**
 * Activity for managing and displaying the match room details.
 *
 * This activity allows users to view match information, manage players in different lists (confirmed, waiting, out),
 * and start the match.
 */
class MatchRoomActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMatchRoomBinding
    private lateinit var viewModel: MatchRoomViewModel

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down, then this Bundle contains the data it most recently supplied in
     * onSaveInstanceState(Bundle). Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        // Set up navigation icon click listener
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(MatchRoomViewModel::class.java)

        // Retrieve match details from the intent
        val match: Match? = intent.getSerializableExtra("MATCH-ROOM") as? Match

        // Fetch match data using the ViewModel
        if (match != null) {
            viewModel.fetchData(match)
        }

        // Observe changes in selected match and update UI accordingly
        viewModel.selectedMatch.observe(this) { match ->
            match?.let {
                binding.codeText.text = it.code
                binding.confirmedListCapacity.text = "(" + it.numberOfPlayers.toString() + " " + "players" + ")"
            }
        }

        // Observe changes in confirmed player list and update the corresponding RecyclerView
        viewModel.confirmedList.observe(this) { players ->
            setupRecyclerView(binding.confirmedRecyclerView, players)
        }

        // Observe changes in waiting player list and update the corresponding RecyclerView
        viewModel.waitingList.observe(this) { players ->
            setupRecyclerView(binding.waitingRecyclerView, players)
        }

        // Observe changes in out player list and update the corresponding RecyclerView
        viewModel.outList.observe(this) { players ->
            setupRecyclerView(binding.outRecyclerView, players)
        }

        // Set up click listeners for buttons
        binding.startMatchButton.setOnClickListener(this)
        binding.confirmButton.setOnClickListener(this)
        binding.outButton.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.startMatchButton -> {
                // Start the match and navigate to the MatchActivity
                val intent = Intent(this, MatchActivity::class.java)
                val playersList = viewModel.confirmedList.value.orEmpty()
                intent.putExtra("PLAYER_LIST", ArrayList(playersList))
                startActivity(intent)
            }
            R.id.confirmButton -> {
                // Update player status to confirmed
                viewModel.updatePlayer(2)
            }
            R.id.outButton -> {
                // Update player status to out
                viewModel.updatePlayer(3)
            }
        }
    }

    /**
     * Sets up a RecyclerView with the provided player list.
     *
     * @param recyclerView The RecyclerView to be set up.
     * @param playerList The list of players to be displayed in the RecyclerView.
     */
    private fun setupRecyclerView(recyclerView: RecyclerView, playerList: List<Player>) {
        val adapter = PlayerAdapter(playerList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
