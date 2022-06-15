package com.example.emparejapp.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.emparejapp.R
import com.example.emparejapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mpBackground : MediaPlayer
    private var playing = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        backgroundSound()
        animations()
        clicks()
    }

    private fun backgroundSound() {
        if (playing){
            mpBackground.stop()
            binding.imgSound.setImageResource(R.drawable.ic_baseline_volume_off_24)
        }else{
            binding.imgSound.setImageResource(R.drawable.ic_baseline_volume_up_24)
            mpBackground = MediaPlayer.create(requireContext(), R.raw.background)
            mpBackground.isLooping = true
            mpBackground.start()
        }
        playing = !playing

    }

    private fun animations() {
        binding.linear.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.down_up))
        binding.imageView4.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.up_down))
    }

    private fun clicks() {
        binding.imgSound.setOnClickListener {
            backgroundSound()
        }
        binding.btnPlay.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val results = arrayOf(validateFirst(),validateSecond())
        if (false in results)
            return

        sendData()
    }

    private fun sendData() {
        val actions = HomeFragmentDirections.actionHomeFragmentToMenuFragment(binding.edtFirst.text.toString(),binding.edtSecond.text.toString())
        binding.edtFirst.text = null
        binding.edtSecond.text = null
        findNavController().navigate(actions)
    }

    private fun validateSecond(): Boolean {
        return if (binding.edtSecond.text.toString().isNullOrEmpty()){
            binding.TilSecond.error = "This field cant be empty"
            false
        }else{
            binding.TilSecond.error = null
            true
        }
    }

    private fun validateFirst(): Boolean {
        return if (binding.edtFirst.text.toString().isNullOrEmpty()){
            binding.TilFirst.error = "This field cant be empty"
            false
        }else{
            binding.TilFirst.error = null
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mpBackground.stop()
        mpBackground.release()
    }
}