package com.example.medicalrehabilitation.view

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.ActivityRestBinding
import com.example.medicalrehabilitation.presenter.RestPresenter

//Класс, отвечающий за работу RestActivity
class RestActivity : AppCompatActivity() {
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании отдыха

    private var plus30Sec: Int = 30000

    private var restPresenter: RestPresenter = RestPresenter()
    private lateinit var binding: ActivityRestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        soundOfStop = MediaPlayer.create(this, R.raw.sound_stop)
        restPresenter.attachView(this)
        binding.recommendationsTextView.text = restPresenter.getRecommendations()
    }

    override fun onResume() {
        super.onResume()
        restPresenter.timerResume(binding.timerTextViewRest, soundOfStop)
        binding.plus30secButton.setOnClickListener { plus30Sec(plus30Sec) }
        binding.nextButton2.setOnClickListener { nextButtonClicked() }
    }

    override fun onPause() {
        super.onPause()
        restPresenter.timerPause()
        restPresenter.soundPause(soundOfStop)
    }

    private fun nextButtonClicked() {
        super.finish()
    }

    private fun plus30Sec(millisPlus: Int) {
        restPresenter.plus30Sec(millisPlus, binding.timerTextViewRest, soundOfStop)
    }
}