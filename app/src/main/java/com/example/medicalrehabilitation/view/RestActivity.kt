package com.example.medicalrehabilitation.view

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.presenter.RestPresenter

//Класс, отвечающий за работу RestActivity
class RestActivity : AppCompatActivity() {
    private lateinit var timerTextView: TextView //Текстовое поле, отображающее время на таймере
    private lateinit var recommendationsTextView: TextView
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании отдыха
    private lateinit var plus30SecButton: Button
    private lateinit var nextButton: Button
    private var plus30Sec: Int = 30000

    private var restPresenter: RestPresenter = RestPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)

        timerTextView = findViewById(R.id.timer_textView_rest)
        recommendationsTextView = findViewById(R.id.recommendations_textView)
        plus30SecButton = findViewById(R.id.plus30sec_button)
        nextButton = findViewById(R.id.next_button2)
        soundOfStop = MediaPlayer.create(this, R.raw.sound_stop)
        restPresenter.attachView(this)
        recommendationsTextView.text = restPresenter.getRecommendations()
    }

    override fun onResume() {
        super.onResume()
        restPresenter.timerResume(timerTextView, soundOfStop)
        buttonClick()
    }

    override fun onPause() {
        super.onPause()
        restPresenter.timerPause()
        restPresenter.soundPause(soundOfStop)
    }

    private fun buttonClick() {
        plus30SecButton.setOnClickListener {
            plus30Sec(plus30Sec)
        }

        nextButton.setOnClickListener {
            super.finish()
        }
    }

    private fun plus30Sec(millisPlus: Int) {
        restPresenter.plus30Sec(millisPlus, timerTextView, soundOfStop)
    }
}