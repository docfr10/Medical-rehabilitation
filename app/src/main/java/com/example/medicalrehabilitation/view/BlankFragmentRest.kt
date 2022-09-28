package com.example.medicalrehabilitation.view

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.medicalrehabilitation.viewmodel.BlankFragmentRestViewModel
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentRestBinding

class BlankFragmentRest : Fragment() {
    private lateinit var viewModel: BlankFragmentRestViewModel
    private lateinit var binding: FragmentBlankFragmentRestBinding

    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании отдыха
    private var plus30Sec: Int = 30000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankFragmentRestBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[BlankFragmentRestViewModel::class.java]

        soundOfStop = MediaPlayer.create(context, R.raw.sound_stop)

        val recommendations: Array<String> = resources.getStringArray(R.array.recommendations)
        binding.recommendationsTextView.text = viewModel.getRecommendations(recommendations)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.timerResume(binding, soundOfStop)
        binding.plus30secButton.setOnClickListener { plus30Sec(plus30Sec) }
        binding.nextButton2.setOnClickListener { nextButtonClicked() }
    }

    override fun onPause() {
        super.onPause()
        viewModel.timerPause()
        viewModel.soundPause(soundOfStop)
    }

    private fun nextButtonClicked() {
        findNavController().navigate(R.id.action_blankFragmentRest_to_blankFragmentTraining)
    }

    private fun plus30Sec(millisPlus: Int) {
        viewModel.plus30Sec(millisPlus, binding, soundOfStop)
    }
}