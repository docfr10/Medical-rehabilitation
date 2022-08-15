package com.example.medicalrehabilitation.presenter

import android.media.MediaPlayer
import android.os.CountDownTimer
import android.widget.TextView

class RestPresenter : RestInterface {
    private var timer: CountDownTimer? = null //Таймер
    private var millisStart: Long = 5000 //120000 //Время отдыха
    private var millisLeft: Long = millisStart //Время, оставщееся до конца отдыха

    //Запуск и проверка таймера на окончание
    override fun timerStart(
        millisInFuture: Long,
        timertextView: TextView,
        soundOfStop: MediaPlayer
    ) {
        timer = object : CountDownTimer(millisInFuture, 1) {
            override fun onTick(p0: Long) {
                millisLeft = p0
                val minutes = (p0 / (1000 * 60))
                val seconds = ((p0 / 1000) - minutes * 60)
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
    override fun timerPause() {
        timer?.cancel()
    }

    //Воспроизведение таймера с того момента когда он остановился
    override fun timerResume(timertextView: TextView, soundOfStop: MediaPlayer) {
        timerStart(millisLeft, timertextView, soundOfStop)
    }

    override fun plus30Sec(millisPlus: Int, timertextView: TextView, soundOfStop: MediaPlayer) {
        timer?.cancel()
        timerStart(millisLeft + millisPlus, timertextView, soundOfStop)
    }

    //Проигрывание звука
    override fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }

    //Остановка звука
    override fun soundPause(sound: MediaPlayer) {
        sound.pause()
    }
}