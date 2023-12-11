package com.example.peladapp.ui.preparematch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import com.example.peladapp.R
import com.example.peladapp.databinding.FragmentPrepareMatchBinding
import com.example.peladapp.ui.matchroom.MatchRoomActivity
import androidx.lifecycle.ViewModelProvider

class PrepareMatchFragment : Fragment(), View.OnClickListener {

    private var binding: FragmentPrepareMatchBinding? = null
    private lateinit var viewModel: PrepareMatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrepareMatchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PrepareMatchViewModel::class.java)

        binding?.createMatchButton?.setOnClickListener(this)
        binding?.insertCodeButton?.setOnClickListener(this)

        binding?.numberOfPlayersPicker?.let { setupNumberPicker(it) }
        binding?.playersPerTeamPicker?.let { setupNumberPicker(it) }

        viewModel.matchCreated.observe(viewLifecycleOwner) { result ->
            if (result) {
                val intent = Intent(requireContext(), MatchRoomActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Error creating Match!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.createMatchButton -> {
                val numberOfPlayers = binding?.numberOfPlayersPicker?.value ?: 0
                val playersPerTeam = binding?.playersPerTeamPicker?.value ?: 0
                viewModel.createMatch(numberOfPlayers, playersPerTeam)
            }
            R.id.insertCodeButton -> {
                // Handle insert code button click
            }
        }
    }

    private fun setupNumberPicker(picker: NumberPicker) {
        picker.minValue = 0
        picker.maxValue = 100
    }
}