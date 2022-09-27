package com.example.medicalrehabilitation.view

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.ActivityRestBinding
import com.example.medicalrehabilitation.presenter.RestPresenter
import com.example.medicalrehabilitation.viewmodel.RestViewModel

//Класс, отвечающий за работу RestActivity
class RestActivity : AppCompatActivity() {
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании отдыха
    private lateinit var binding: ActivityRestBinding
    private lateinit var viewModel: RestViewModel

    private var plus30Sec: Int = 30000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val provider = ViewModelProvider(this)
        viewModel = provider.get(RestViewModel::class.java)

        soundOfStop = MediaPlayer.create(this, R.raw.sound_stop)

        val recommendations: Array<String> = resources.getStringArray(R.array.recommendations)
        binding.recommendationsTextView.text = viewModel.getRecommendations(recommendations)
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
        super.finish()
    }

    private fun plus30Sec(millisPlus: Int) {
        viewModel.plus30Sec(millisPlus, binding, soundOfStop)
    }
}