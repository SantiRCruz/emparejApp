package com.example.emparejapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.emparejapp.R
import com.example.emparejapp.core.Constants
import com.example.emparejapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding
    private var time = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        animations()
        getTimePreferences()
        clicks()

    }

    private fun getTimePreferences() {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val timeShared = sharedPreferences.getInt(Constants.KEY_TIME,0)
        if (timeShared != 0){
            binding.checkBox.isChecked = true
            binding.seekBar.progress = when(timeShared){
                10000->{
                    binding.txtSeg.text = "10 seg"
                    time = 10000
                    0}
                20000->{
                    binding.txtSeg.text = "20 seg"
                    time = 20000
                    1}
                30000->{
                    binding.txtSeg.text = "30 seg"
                    time = 30000
                    2}
                40000->{
                    binding.txtSeg.text = "40 seg"
                    time = 40000
                    3}
                50000-> {
                    binding.txtSeg.text = "50 seg"
                    time = 50000
                    4}
                else ->{0}
            }
        }else{
            binding.checkBox.isChecked = false
        }
    }

    private fun animations() {
        binding.linear.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.up_down))
        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.seekLinear.visibility = View.VISIBLE
                binding.txtSeg.text = "10 seg"
                time = 10000
            }else{
                binding.seekLinear.visibility = View.GONE
            }
        }
        binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.apply {
                     when (progress) {
                        0 -> {
                            binding.txtSeg.text = "10 Seg"
                            time = 10000
                        }
                        1 -> {
                            binding.txtSeg.text = "20 Seg"
                            time = 20000
                        }
                        2 -> {
                            binding.txtSeg.text = "30 Seg"
                            time = 30000
                        }
                        3 -> {
                            binding.txtSeg.text = "40 Seg"
                            time = 40000
                        }
                        4 -> {
                            binding.txtSeg.text = "50 Seg"
                            time = 50000
                        }
                        else -> {"0 Seg"}
                    }
                }
            }
        })
    }

    private fun clicks() {
        binding.btnBack.setOnClickListener {
            isCheckedTime()
            findNavController().popBackStack()
        }

        binding.btnEasy.setOnClickListener {
            isCheckedTime()
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            with(sharedPreferences.edit()){
                putInt(Constants.KEY_SETTINGS,1)
                apply()
            }
            findNavController().popBackStack()
        }
        binding.btnMedium.setOnClickListener {
            isCheckedTime()
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            with(sharedPreferences.edit()){
                putInt(Constants.KEY_SETTINGS,2)
                apply()
            }
            findNavController().popBackStack()
        }
        binding.btnHard.setOnClickListener {
            isCheckedTime()
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            with(sharedPreferences.edit()){
                putInt(Constants.KEY_SETTINGS,3)
                apply()
            }
            findNavController().popBackStack()
        }
    }
    private fun isCheckedTime(){
        if (binding.checkBox.isChecked){
            val sharedPreferences  = requireActivity().getPreferences(Context.MODE_PRIVATE)
            with(sharedPreferences.edit()){
                putInt(Constants.KEY_TIME,time)
                apply()
            }
        }else if (!binding.checkBox.isChecked){
            val sharedPreferences  = requireActivity().getPreferences(Context.MODE_PRIVATE)
            with(sharedPreferences.edit()){
                putInt(Constants.KEY_TIME,0)
                apply()
            }
        }
    }
}