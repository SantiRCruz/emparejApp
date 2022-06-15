package com.example.emparejapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.emparejapp.R
import com.example.emparejapp.core.Constants
import com.example.emparejapp.core.Results
import com.example.emparejapp.data.AppDatabase
import com.example.emparejapp.data.models.PlayerEntity
import com.example.emparejapp.databinding.ActivityGameBinding
import com.example.emparejapp.databinding.DailogFinishBinding
import com.example.emparejapp.databinding.FragmentGameBinding
import com.example.emparejapp.presentation.PointsViewModel
import com.example.emparejapp.presentation.PointsViewModelFactory


class GameFragment : Fragment(R.layout.fragment_game) {
    private lateinit var binding: FragmentGameBinding
    private val args by navArgs<GameFragmentArgs>()
    private val viewModel by viewModels<PointsViewModel> {
        PointsViewModelFactory(
            AppDatabase.getDb(
                requireContext()
            ).PlayerDao()
        )
    }
    private val imageArray = arrayOf(1, 2, 3, 4)
    private lateinit var image1: ImageView
    private lateinit var image2: ImageView
    private var currentImage = 1
    private var currentTurn = 1
    private var points1 = 0
    private var points2 = 0
    private lateinit var mp: MediaPlayer
    private var timeShared = 0
    private  var countDownTimer: CountDownTimer ?= null
    private var actualTime = 0
    private var finalTime = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameBinding.bind(view)
        points1 = 0
        points2 = 0
        currentImage = 1
        currentTurn = 1

        if (makeTimer()) {
            binding.chronometer.start()
        } else {
            binding.chronometer.visibility = View.GONE
            countDownTimer = object : CountDownTimer(timeShared.toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.txtChronometer.text =
                        ((millisUntilFinished / 1000).toInt() + 1).toString()
                    actualTime = ((millisUntilFinished / 1000).toInt() + 1)
                }

                override fun onFinish() {
                    disableAll()
                    val dialogBinding =
                        DailogFinishBinding.inflate(LayoutInflater.from(requireContext()))
                    val alertDialog = AlertDialog.Builder(requireContext()).apply {
                        setView(dialogBinding.root)
                    }.create()



                     if (actualTime != 1 ){
                         dialogBinding.txtTime.text = "$actualTime Seconds"
                        finalTime = actualTime

                    }else{
                         dialogBinding.txtTime.text =(timeShared / 1000).toString() + " Seconds"
                         finalTime = (timeShared / 1000)

                     }

                    saveData(args.player1, points1, finalTime.toString())
                    saveData(args.player2, points2, finalTime.toString())
                    dialogBinding.txtFirst.text = args.player1
                    dialogBinding.txtSecond.text = args.player2
                    dialogBinding.txtFirstPoints.text = "Score: $points1 pts"
                    dialogBinding.txtSecondPoints.text = "Score: $points2 pts"
                    dialogBinding.btnNewGame.setOnClickListener {
                        alertDialog.dismiss()
                        findNavController().popBackStack()
                    }
                    dialogBinding.btnScores.setOnClickListener {
                        alertDialog.dismiss()
                        findNavController().navigate(R.id.action_gameFragment_to_scoresFragment)
                    }
                    dialogBinding.btnShare.setOnClickListener {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "WS EmparejApp \n" +
                                    "${args.player1} : $points1 \n \n " +
                                    "${args.player2} : $points2 \n \n" +
                                    "Nice Game!")
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        requireActivity().startActivity(shareIntent)
                    }

                    dialogBinding.imgExit.setOnClickListener {
                        alertDialog.dismiss()
                    }

                    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    alertDialog.setCancelable(false)
                    alertDialog.show()

                }
            }.start()
        }


        setNames()
        setImageTags()
        clicks()
    }

    private fun makeTimer(): Boolean {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        timeShared = sharedPreferences.getInt(Constants.KEY_TIME, 0)
        return timeShared == 0
    }


    private fun setNames() {
        binding.txtFirst.text = args.player1
        binding.txtSecond.text = args.player2
    }

    private fun setImageTags() {
        binding.btnA1.tag = "0"
        binding.btnA2.tag = "1"
        binding.btnB1.tag = "2"
        binding.btnB2.tag = "3"

        imageArray.shuffle()

    }

    private fun clicks() {

        binding.btnExit.setOnClickListener {
            mp.stop()
            findNavController().popBackStack()
        }

        binding.btnA1.setOnClickListener { selection(it as ImageView) }
        binding.btnA2.setOnClickListener { selection(it as ImageView) }
        binding.btnB1.setOnClickListener { selection(it as ImageView) }
        binding.btnB2.setOnClickListener { selection(it as ImageView) }
    }

    private fun selection(img: ImageView) {
        mp = MediaPlayer.create(requireContext(), R.raw.touch)
        mp.start()
        val tag = img.tag.toString().toInt()
        if (imageArray[tag] == 1) {
            img.setImageResource(R.drawable.homero)
        } else if (imageArray[tag] == 2) {
            img.setImageResource(R.drawable.lisa)
        } else if (imageArray[tag] == 3) {
            img.setImageResource(R.drawable.homero)
        } else if (imageArray[tag] == 4) {
            img.setImageResource(R.drawable.lisa)
        }

        if (currentImage == 1) {
            image1 = img
            currentImage = 2
            img.isEnabled = false
        } else if (currentImage == 2) {
            image2 = img
            currentImage = 1
            img.isEnabled = false
            disableAll()
            val h = Handler(Looper.getMainLooper())
            h.postDelayed({ validateImage() }, 1000)
        }


    }

    private fun validateImage() {
        if (image1.drawable.constantState == image2.drawable.constantState) {
            mp = MediaPlayer.create(requireContext(), R.raw.success)
            mp.start()
            if (currentTurn == 1) {
                points1 += 100
                binding.txtFirstPoints.text = "${points1.toString()} pts "
            } else if (currentTurn == 2) {
                points2 += 100
                binding.txtSecondPoints.text = "${points2.toString()} pts "
            }
            image1.isEnabled = false
            image2.isEnabled = false
            image1.tag = ""
            image2.tag = ""
        } else {
            mp = MediaPlayer.create(requireContext(), R.raw.no)
            mp.start()
            image1.setImageResource(R.drawable.oculta)
            image2.setImageResource(R.drawable.oculta)
            if (currentTurn == 1) {
                currentTurn = 2
                points1 -= 2
                binding.txtFirstPoints.text = "${points1.toString()} pts "
                binding.txtSecond.setTextColor(resources.getColor(R.color.black_2))
                binding.txtFirst.setTextColor(resources.getColor(R.color.gray))

            } else if (currentTurn == 2) {
                currentTurn = 1
                points2 -= 2
                binding.txtSecondPoints.text = "${points2.toString()} pts "
                binding.txtSecond.setTextColor(resources.getColor(R.color.gray))
                binding.txtFirst.setTextColor(resources.getColor(R.color.black_2))
            }
        }
        binding.btnA1.isEnabled = !binding.btnA1.tag.toString().isEmpty()
        binding.btnA2.isEnabled = !binding.btnA2.tag.toString().isEmpty()
        binding.btnB1.isEnabled = !binding.btnB1.tag.toString().isEmpty()
        binding.btnB2.isEnabled = !binding.btnB2.tag.toString().isEmpty()
        verifyFinal()
    }

    private fun verifyFinal() {
        if (binding.btnA1.tag.toString().isEmpty() && binding.btnA2.tag.toString()
                .isEmpty() && binding.btnB1.tag.toString().isEmpty() && binding.btnB2.tag.toString()
                .isEmpty()
        ) {
            if (makeTimer()) {
                binding.chronometer.stop()
                val dialogBinding = DailogFinishBinding.inflate(LayoutInflater.from(requireContext()))
                val alertDialog = AlertDialog.Builder(requireContext()).apply {
                    setView(dialogBinding.root)
                }.create()

                mp = MediaPlayer.create(requireContext(), R.raw.win)
                mp.start()
                saveData(args.player1, points1, binding.chronometer.text.toString())
                saveData(args.player2, points2, binding.chronometer.text.toString())

                dialogBinding.txtTime.text = binding.chronometer.text.toString()
                dialogBinding.txtFirst.text = args.player1
                dialogBinding.txtSecond.text = args.player2
                dialogBinding.txtFirstPoints.text = "Score: $points1 pts"
                dialogBinding.txtSecondPoints.text = "Score: $points2 pts"
                dialogBinding.btnNewGame.setOnClickListener {
                    alertDialog.dismiss()
                    findNavController().popBackStack()
                }
                dialogBinding.btnScores.setOnClickListener {
                    alertDialog.dismiss()
                    findNavController().navigate(R.id.action_gameFragment_to_scoresFragment)
                }
                dialogBinding.btnShare.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "WS EmparejApp \n" +
                                "${args.player1} : $points1 \n \n " +
                                "${args.player2} : $points2 \n \n" +
                                "Nice Game!")
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    requireActivity().startActivity(shareIntent)
                }
                dialogBinding.imgExit.setOnClickListener {
                    alertDialog.dismiss()
                }

                alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                alertDialog.setCancelable(false)
                alertDialog.show()

            } else {
                countDownTimer!!.onFinish()
                countDownTimer!!.cancel()
            }
        }

    }

    private fun saveData(name: String, points: Int, time: String) {
        var p = points
        if (p <= 0) {
            p = 0
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.savePlayer(PlayerEntity(0, name, p, 1, time)).collect {
                when (it) {
                    is Results.Success -> {
                    }
                }
            }
        }
    }

    private fun disableAll() {
        binding.btnA1.isEnabled = false
        binding.btnA2.isEnabled = false
        binding.btnB1.isEnabled = false
        binding.btnB2.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null){
            countDownTimer!!.cancel()
        }
    }
}