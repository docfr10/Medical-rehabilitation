package com.example.medicalrehabilitation.viewmodel

import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentRestBinding
import java.util.*

class BlankFragmentRestViewModel : ViewModel() {
    private var timer: CountDownTimer? = null //Таймер
    private var millisStart: Long = 120000 //Время отдыха
    private var millisLeft: Long = millisStart //Время, оставщееся до конца отдыха

    //Запуск и проверка таймера на окончание
    private fun timerStart(
        millisInFuture: Long,
        binding: FragmentBlankFragmentRestBinding,
        soundOfStop: MediaPlayer
    ) {
        timer = object : CountDownTimer(millisInFuture, 1) {
            override fun onTick(p0: Long) {
                millisLeft = p0
                val minutes = (p0 / (1000 * 60))
                val seconds = ((p0 / 1000) - minutes * 60)
                binding.timerTextViewRest.text = "$minutes:$seconds"
            }

            override fun onFinish() {
                binding.timerTextViewRest.text = "Закончили"
                soundPlay(soundOfStop)
            }
        }
        (timer as CountDownTimer).start()
    }

    //Постановка таймера на паузу
    fun timerPause() {
        timer?.cancel()
    }

    //Воспроизведение таймера с того момента когда он остановился
    fun timerResume(binding: FragmentBlankFragmentRestBinding, soundOfStop: MediaPlayer) {
        timerStart(millisLeft, binding, soundOfStop)
    }

    fun plus30Sec(millisPlus: Int, binding: FragmentBlankFragmentRestBinding, soundOfStop: MediaPlayer) {
        timer?.cancel()
        timerStart(millisLeft + millisPlus, binding, soundOfStop)
    }

    //Проигрывание звука
    fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }

    //Остановка звука
    fun soundPause(sound: MediaPlayer) {
        sound.pause()
    }


    fun getRecommendations(recommendations: Array<String>): String {
        val randomIndex = Random().nextInt(recommendations.size)
        println("RANDOM: $randomIndex")
        return recommendations[randomIndex]
    }
}