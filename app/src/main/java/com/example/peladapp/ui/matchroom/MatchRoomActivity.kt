package com.example.peladapp.ui.matchroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.peladapp.ui.match.MatchActivity
import com.example.peladapp.R
import com.example.peladapp.databinding.ActivityMatchRoomBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peladapp.UserAdapter

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

        // Set up RecyclerView adapters
        setupRecyclerView(binding.confirmedRecyclerView, viewModel.confirmedList)
        setupRecyclerView(binding.waitingRecyclerView, viewModel.waitingList)
        setupRecyclerView(binding.outRecyclerView, viewModel.outList)

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

    private fun setupRecyclerView(recyclerView: RecyclerView, userList: List<String>) {
        val adapter = UserAdapter(userList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
