package com.example.medicalrehabilitation

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

//Класс, отвечающий за работу RestActivity
class Rest : AppCompatActivity() {
    private lateinit var timertextView: TextView //Текстовое поле, отображающее время на таймере
    private var timer: CountDownTimer? = null //Таймер
    private var millisStart: Long = 30000; //120000 //Время отдыха
    private var millisLeft: Long = millisStart //Время, оставщееся до конца отдыха
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании отдыха
    private lateinit var plus30SecButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)

        timertextView = findViewById(R.id.timer_textView_rest)
        plus30SecButton = findViewById(R.id.plus30sec_button)
        nextButton = findViewById(R.id.next_button2)
        soundOfStop = MediaPlayer.create(this, R.raw.sound_stop)
    }

    override fun onResume() {
        super.onResume()
        timerResume()
        buttonClick()
    }

    override fun onPause() {
        super.onPause()
        timerPause()
        soundPause(soundOfStop)
    }

    private fun buttonClick() {
        plus30SecButton.setOnClickListener {
            plus30Sec(30000)
        }

        nextButton.setOnClickListener {
            super.finish()
        }
    }


    //Запуск и проверка таймера на окончание
    private fun timerStart(millisInFuture: Long) {
        timer = object : CountDownTimer(millisInFuture, 1) {
            override fun onTick(p0: Long) {
                millisLeft = p0
                val minutes = (p0 / (1000 * 60));
                val seconds = ((p0 / 1000) - minutes * 60);
                timertextView.text = "$minutes:$seconds"
            }

            override fun onFinish() {
                timertextView.text = "Закончили"
                soundPlay(soundOfStop)
            }
        }
        (timer as CountDownTimer).start()
    }

    //Постановка таймера на паузу
    private fun timerPause() {
        timer?.cancel()
    }

    //Воспроизведение таймера с того момента когда он остановился
    private fun timerResume() {
        timerStart(millisLeft);
    }

    private fun plus30Sec(millisPlus: Long) {
        timer?.cancel()
        timerStart(millisLeft + millisPlus)
    }

    //Проигрывание звука
    private fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }

    //Остановка звука
    private fun soundPause(sound: MediaPlayer) {
        sound.pause()
    }
}