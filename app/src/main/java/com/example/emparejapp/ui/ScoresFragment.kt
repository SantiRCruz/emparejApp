package com.example.emparejapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.emparejapp.R
import com.example.emparejapp.core.Results
import com.example.emparejapp.data.AppDatabase
import com.example.emparejapp.databinding.FragmentScoresBinding
import com.example.emparejapp.presentation.PointsViewModel
import com.example.emparejapp.presentation.PointsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect


class ScoresFragment : Fragment(R.layout.fragment_scores) {
    private lateinit var binding: FragmentScoresBinding
    private val viewModel by viewModels<PointsViewModel> {
        PointsViewModelFactory(
            AppDatabase.getDb(
                requireContext()
            ).PlayerDao()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScoresBinding.bind(view)

        animations()
        obtainEasyData()
        clicks()
    }

    private fun clicks() {
        binding.btnEasy.setOnClickListener { obtainEasyData() }
        binding.btnMedium.setOnClickListener { obtainMediumData() }
        binding.btnHard.setOnClickListener { obtainHardData() }
    }

    private fun animations() {
        binding.linear.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.down_up
            )
        )
    }

    private fun obtainEasyData() {
        nullData()
        binding.btnEasy.setTextColor(resources.getColor(R.color.white, null))
        binding.btnMedium.setTextColor(resources.getColor(R.color.gray, null))
        binding.btnHard.setTextColor(resources.getColor(R.color.gray, null))
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.fetchPlayers(1).collect {
                when (it) {
                    is Results.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.linear.visibility = View.GONE
                            Snackbar.make(
                                binding.root,
                                "No Scores in this level",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            binding.linear.visibility = View.VISIBLE
                            for (i in 0 until it.data.size) {
                                when (i) {
                                    0 -> {
                                        binding.txt1.text = it.data[i].name
                                        binding.txtP1.text = it.data[i].points.toString()
                                        binding.txtTime1.text = it.data[i].time
                                        binding.img1.visibility = View.VISIBLE

                                    }
                                    1 -> {
                                        binding.txt2.text = it.data[i].name
                                        binding.txtP2.text = it.data[i].points.toString()
                                        binding.txtTime2.text = it.data[i].time
                                        binding.img2.visibility = View.VISIBLE

                                    }
                                    2 -> {
                                        binding.txt3.text = it.data[i].name
                                        binding.txtP3.text = it.data[i].points.toString()
                                        binding.txtTime3.text = it.data[i].time
                                        binding.img3.visibility = View.VISIBLE

                                    }
                                    3 -> {
                                        binding.txt4.text = it.data[i].name
                                        binding.txtP4.text = it.data[i].points.toString()
                                        binding.txtTime4.text = it.data[i].time
                                        binding.img4.visibility = View.VISIBLE

                                    }
                                    4 -> {
                                        binding.txt5.text = it.data[i].name
                                        binding.txtP5.text = it.data[i].points.toString()
                                        binding.txtTime5.text = it.data[i].time
                                        binding.img5.visibility = View.VISIBLE

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun obtainMediumData() {
        nullData()
        binding.btnEasy.setTextColor(resources.getColor(R.color.gray, null))
        binding.btnMedium.setTextColor(resources.getColor(R.color.white, null))
        binding.btnHard.setTextColor(resources.getColor(R.color.gray, null))
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.fetchPlayers(2).collect {
                when (it) {
                    is Results.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.linear.visibility = View.GONE
                            Snackbar.make(
                                binding.root,
                                "No Scores in this level",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            binding.linear.visibility = View.VISIBLE
                            for (i in 0 until it.data.size) {
                                when (i) {
                                    0 -> {
                                        binding.txt1.text = it.data[i].name
                                        binding.txtP1.text = it.data[i].points.toString()
                                        binding.txtTime1.text = it.data[i].time
                                        binding.img1.visibility = View.VISIBLE
                                    }
                                    1 -> {
                                        binding.txt2.text = it.data[i].name
                                        binding.txtP2.text = it.data[i].points.toString()
                                        binding.txtTime2.text = it.data[i].time
                                        binding.img2.visibility = View.VISIBLE
                                    }
                                    2 -> {
                                        binding.txt3.text = it.data[i].name
                                        binding.txtP3.text = it.data[i].points.toString()
                                        binding.txtTime3.text = it.data[i].time
                                        binding.img3.visibility = View.VISIBLE
                                    }
                                    3 -> {
                                        binding.txt4.text = it.data[i].name
                                        binding.txtP4.text = it.data[i].points.toString()
                                        binding.txtTime4.text = it.data[i].time
                                        binding.img4.visibility = View.VISIBLE
                                    }
                                    4 -> {
                                        binding.txt5.text = it.data[i].name
                                        binding.txtP5.text = it.data[i].points.toString()
                                        binding.txtTime5.text = it.data[i].time
                                        binding.img5.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun obtainHardData() {
        nullData()
        binding.btnEasy.setTextColor(resources.getColor(R.color.gray, null))
        binding.btnMedium.setTextColor(resources.getColor(R.color.gray, null))
        binding.btnHard.setTextColor(resources.getColor(R.color.white, null))
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.fetchPlayers(3).collect {
                when (it) {
                    is Results.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            binding.linear.visibility = View.GONE
                            Snackbar.make(
                                binding.root,
                                "No Scores in this level",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            binding.linear.visibility = View.VISIBLE
                            for (i in 0 until it.data.size) {
                                when (i) {
                                    0 -> {
                                        binding.txt1.text = it.data[i].name
                                        binding.txtP1.text = it.data[i].points.toString()
                                        binding.txtTime1.text = it.data[i].time
                                        binding.img1.visibility = View.VISIBLE
                                    }
                                    1 -> {
                                        binding.txt2.text = it.data[i].name
                                        binding.txtP2.text = it.data[i].points.toString()
                                        binding.txtTime2.text = it.data[i].time
                                        binding.img2.visibility = View.VISIBLE
                                    }
                                    2 -> {
                                        binding.txt3.text = it.data[i].name
                                        binding.txtP3.text = it.data[i].points.toString()
                                        binding.txtTime3.text = it.data[i].time
                                        binding.img3.visibility = View.VISIBLE
                                    }
                                    3 -> {
                                        binding.txt4.text = it.data[i].name
                                        binding.txtP4.text = it.data[i].points.toString()
                                        binding.txtTime4.text = it.data[i].time
                                        binding.img4.visibility = View.VISIBLE
                                    }
                                    4 -> {
                                        binding.txt5.text = it.data[i].name
                                        binding.txtP5.text = it.data[i].points.toString()
                                        binding.txtTime5.text = it.data[i].time
                                        binding.img5.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun nullData() {
        binding.txt1.text = ""
        binding.txtP1.text = ""
        binding.txtTime1.text = ""
        binding.txt2.text = ""
        binding.txtP2.text = ""
        binding.txtTime2.text = ""
        binding.txt3.text = ""
        binding.txtP3.text = ""
        binding.txtTime3.text = ""
        binding.txt4.text = ""
        binding.txtP4.text = ""
        binding.txtTime4.text = ""
        binding.txt5.text = ""
        binding.txtP5.text = ""
        binding.txtTime5.text = ""
        binding.img1.visibility = View.GONE
        binding.img2.visibility = View.GONE
        binding.img3.visibility = View.GONE
        binding.img4.visibility = View.GONE
        binding.img5.visibility = View.GONE
    }

}