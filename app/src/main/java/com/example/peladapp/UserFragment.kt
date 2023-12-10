package com.example.peladapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.peladapp.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private var binding: FragmentUserBinding? = null
    private lateinit var sp: AppPreferences
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

        binding?.logOutButton?.setOnClickListener {
            sp.logOutUser()
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}