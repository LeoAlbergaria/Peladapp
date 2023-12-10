package com.example.peladapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.peladapp.databinding.FragmentPrepareMatchBinding
import com.example.peladapp.databinding.FragmentUserBinding

class PrepareMatchFragment : Fragment(), View.OnClickListener {

    private var binding: FragmentPrepareMatchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrepareMatchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.createMatchButton?.setOnClickListener(this)
        binding?.insertCodeButton?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.createMatchButton -> {
                val intent = Intent(requireContext(), MatchRoomActivity::class.java)
                startActivity(intent)
            }
            R.id.insertCodeButton -> {
                val intent = Intent(requireContext(), MatchRoomActivity::class.java)
                startActivity(intent)
            }
        }
    }
}