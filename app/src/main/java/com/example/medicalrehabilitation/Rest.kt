package com.example.medicalrehabilitation

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView

class Rest : AppCompatActivity() {
    private lateinit var timertextView: TextView //Текстовое поле, отображающее время на таймере
    private var timer: CountDownTimer? = null //Таймер
    private var millisStart: Long = 2000; //120000 //Время отдыха
    private var millisLeft: Long = millisStart //Время, оставщееся до конца отдыха
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании отдыха

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)

        timertextView = findViewById(R.id.timer_textView_rest)
        soundOfStop = MediaPlayer.create(this, R.raw.sound_stop)
    }

    override fun onResume() {
        super.onResume()
        timerResume()
    }

    override fun onPause() {
        super.onPause()
        timerPause()
        soundPause(soundOfStop)
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

    //Проигрывание звука
    private fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }

    //Остановка звука
    private fun soundPause(sound: MediaPlayer) {
        sound.pause()
    }
}