package com.tfg.nbapredictor.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tfg.nbapredictor.databinding.FragmentProfileBinding
import com.tfg.nbapredictor.util.Session

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserData()
    }

    private fun loadUserData() {
        val user = Session.getCurrentUser()
        user?.let {
            binding.tvUsername.text = it.username
            binding.tvEmail.text = it.email
            binding.tvPoints.text = it.points.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
