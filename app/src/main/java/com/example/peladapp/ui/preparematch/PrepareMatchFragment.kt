package com.example.peladapp.ui.preparematch

import android.app.AlertDialog
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

/**
 * Fragment for preparing and creating a match.
 *
 * This fragment allows users to create a new match or join an existing match by entering a code.
 */
class PrepareMatchFragment : Fragment(), View.OnClickListener {

    private var binding: FragmentPrepareMatchBinding? = null
    private lateinit var viewModel: PrepareMatchViewModel

    /**
     * Called to create the view for this fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrepareMatchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned,
     * but before any saved state has been restored in to the view.
     *
     * @param view The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PrepareMatchViewModel::class.java)

        // Set up click listeners
        binding?.createMatchButton?.setOnClickListener(this)
        binding?.insertCodeButton?.setOnClickListener(this)

        // Set up number pickers
        binding?.numberOfPlayersPicker?.let { setupNumberPicker(it) }
        binding?.playersPerTeamPicker?.let { setupNumberPicker(it) }

        // Observe the match creation result
        viewModel.matchCreated.observe(viewLifecycleOwner) { match ->
            if (match != null) {
                // Navigate to the MatchRoomActivity upon successful match creation
                val intent = Intent(requireContext(), MatchRoomActivity::class.java)
                intent.putExtra("MATCH-ROOM", match)
                startActivity(intent)
            } else {
                // Display an error message if match creation fails
                Toast.makeText(requireContext(), "Error creating Match!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Called when the view previously created by onCreateView(LayoutInflater, ViewGroup, Bundle) has been detached from the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.createMatchButton -> {
                // Handle click on the create match button
                val numberOfPlayers = binding?.numberOfPlayersPicker?.value ?: 0
                val playersPerTeam = binding?.playersPerTeamPicker?.value ?: 0
                viewModel.createMatch(numberOfPlayers, playersPerTeam)
            }
            R.id.insertCodeButton -> {
                // Handle click on the insert code button
                val enteredCode = binding?.codeEditText?.text.toString()
                if (enteredCode.isNotEmpty()) {
                    viewModel.enterExistingMatch(enteredCode)
                } else {
                    // Display a message for empty code field
                    Toast.makeText(requireContext(), "Please enter a code", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Sets up the configuration for a NumberPicker.
     *
     * @param picker The NumberPicker to be configured.
     */
    private fun setupNumberPicker(picker: NumberPicker) {
        picker.minValue = 0
        picker.maxValue = 100
    }
}
