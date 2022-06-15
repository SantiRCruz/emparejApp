package com.example.emparejapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.emparejapp.R
import com.example.emparejapp.core.Constants
import com.example.emparejapp.databinding.FragmentMenuBinding


class MenuFragment : Fragment(R.layout.fragment_menu) {
    private lateinit var binding: FragmentMenuBinding
    private val args by navArgs<MenuFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)
        animations()
        clicks()
    }

    private fun animations() {
        binding.linear.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.up_down))

    }

    private fun clicks() {

        binding.btnPlay.setOnClickListener {
            moveToPlay()
        }
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnSettings.setOnClickListener { findNavController().navigate(R.id.action_menuFragment_to_settingsFragment) }
        binding.btnScores.setOnClickListener { findNavController().navigate(R.id.action_menuFragment_to_scoresFragment) }
    }

    private fun moveToPlay() {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        var difficult = sharedPreferences.getInt(Constants.KEY_SETTINGS,1)
        val random = (1 until 3).random()
        if (difficult==1){
            if (random == 1){
                val actions = MenuFragmentDirections.actionMenuFragmentToGameFragment(args.player1,args.player2)
                findNavController().navigate(actions)
            }else if(random == 2){
                val actions = MenuFragmentDirections.actionMenuFragmentToGameFragment(args.player2,args.player1)
                findNavController().navigate(actions)
            }
        }else if (difficult == 2){
            if (random == 1){
                val actions = MenuFragmentDirections.actionMenuFragmentToMediumFragment(args.player1,args.player2)
                findNavController().navigate(actions)
            }else if(random == 2){
                val actions = MenuFragmentDirections.actionMenuFragmentToMediumFragment(args.player2,args.player1)
                findNavController().navigate(actions)
            }
        }else if(difficult == 3){
            if (random == 1){
                val actions = MenuFragmentDirections.actionMenuFragmentToHardFragment(args.player1,args.player2)
                findNavController().navigate(actions)
            }else if(random == 2){
                val actions = MenuFragmentDirections.actionMenuFragmentToHardFragment(args.player2,args.player1)
                findNavController().navigate(actions)
            }
            }
    }
}