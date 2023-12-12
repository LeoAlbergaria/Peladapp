package com.example.peladapp.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.peladapp.AppPreferences
import com.example.peladapp.databinding.FragmentUserBinding

/**
 * Fragment for managing user-related functionality and UI.
 *
 * This fragment allows users to view and edit their profile information.
 */
class UserFragment : Fragment() {

    private var binding: FragmentUserBinding? = null
    private lateinit var sp: AppPreferences
    private lateinit var viewModel: UserViewModel

    /**
     * Initializes the fragment's UI.
     *
     * @param inflater The layout inflater.
     * @param container The parent view group.
     * @param savedInstanceState The saved state of the fragment.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    /**
     * Configures the fragment's behavior after the view is created.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved state of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = AppPreferences.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Log out button click listener
        binding?.logOutButton?.setOnClickListener {
            sp.logOutUser()
            activity?.onBackPressed()
        }

        // Set initial username as a hint in the edit text
        val username = viewModel.getUserName()
        username?.let {
            binding?.editNameEditText?.hint = username
        }

        // Edit name button click listener
        binding?.editNameButton?.setOnClickListener{
            val username = binding?.editNameEditText?.text?.toString()
            username?.let {
                // Change the username through the ViewModel
                viewModel.changeUserName(username) { isSuccess ->
                    if (isSuccess) {
                        Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed to change name!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    /**
     * Cleans up resources when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
