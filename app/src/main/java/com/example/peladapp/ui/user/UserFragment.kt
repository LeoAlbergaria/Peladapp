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

class UserFragment : Fragment() {

    private var binding: FragmentUserBinding? = null
    private lateinit var sp: AppPreferences
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = AppPreferences.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding?.logOutButton?.setOnClickListener {
            sp.logOutUser()
            activity?.onBackPressed()
        }


        binding?.editNameButton?.setOnClickListener{
            val username = binding?.editNameEditText?.text?.toString()
            username?.let {
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}