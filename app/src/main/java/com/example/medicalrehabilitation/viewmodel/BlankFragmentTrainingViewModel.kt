package com.example.medicalrehabilitation.viewmodel

import android.app.AlertDialog
import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentTrainingBinding
import java.util.*

class BlankFragmentTrainingViewModel : ViewModel() {
    //Ссылка на видео, которое будет проигрываться
    private var timer: CountDownTimer? = null //Таймер

    private var millisStart: Long = 300000 //Время выполнения упражнения
    private var millisLeft: Long = millisStart //Время, оставщееся до конца упражнения

    private var millisStartOnRest: Long = 120000 //Время отдыха
    private var millisLeftOnRest: Long = millisStartOnRest //Время, оставщееся до конца отдыха
    private var plus30SecOnRest: Int = 30000

    private var end: Boolean =
        false //Параметр, определяюший завершился ли таймер, необходим для повторного невоспроизведения звука завершения упражнения

    private var myVideoUri =
        Uri.parse("android.resource://com.example.medicalrehabilitation/" + R.raw.video1)

    var counterNumberOfTraining = MutableLiveData<Int>().apply { postValue(1) }

    //Воспроизведение видео
    fun videoPlay(binding: FragmentBlankFragmentTrainingBinding) {
        binding.trainingVideoView.setVideoURI(myVideoUri)
        binding.trainingVideoView.start()
        binding.trainingVideoView.setOnPreparedListener { it.isLooping = true }
    }

    //Остановка видео
    fun videoPause(binding: FragmentBlankFragmentTrainingBinding) {
        binding.trainingVideoView.setVideoURI(myVideoUri)
        binding.trainingVideoView.pause()
    }

    //Смена видео
    fun videoChange(binding: FragmentBlankFragmentTrainingBinding) {
        when (counterNumberOfTraining.value) {
            1 -> {
                this.myVideoUri =
                    Uri.parse(
                        "android.resource://com.example.medicalrehabilitation/" + R.raw.video2
                    )
                counterNumberOfTraining.value = 2
                binding.timerTextView.visibility = View.GONE
                binding.pauseButton.visibility = View.GONE
            }
            2 -> {
                this.myVideoUri =
                    Uri.parse(
                        "android.resource://com.example.medicalrehabilitation/" + R.raw.video3
                    )
                counterNumberOfTraining.value = 3
            }
            3 -> {
                this.myVideoUri =
                    Uri.parse(
                        "android.resource://com.example.medicalrehabilitation/" + R.raw.video4
                    )
                counterNumberOfTraining.value = 4
            }
            4 -> {
                counterNumberOfTraining.value = 0
            }
        }
        videoPlay(binding)
    }


    fun changeDescription(binding: FragmentBlankFragmentTrainingBinding) {
        when (counterNumberOfTraining.value) {
            2 -> {
                binding.exerciseTextView.setText(R.string.description2)
            }
            3 -> {
                binding.exerciseTextView.setText(R.string.description3)
            }
            4 -> {
                binding.exerciseTextView.setText(R.string.description4)
            }
        }
    }


    //Проигрывание звука
    fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }

    //Остановка звука
    fun soundPause(sound: MediaPlayer) {
        sound.pause()
    }

    //Смена информации об упражнении, реализована в виде диалогового окна
    fun aboutExercise(builder: AlertDialog.Builder) {
        when (counterNumberOfTraining.value) {
            1 -> builder.setMessage(R.string.exercise1)
            2, 3, 4 -> builder.setMessage(R.string.exercise2_3_4)
        }
        builder.setTitle((R.string.equipment))
            .setPositiveButton((R.string.clear)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }

    //Запуск и проверка таймера на окончание
    private fun timerStart(
        isRestTimer: Boolean,
        millisInFuture: Long,
        soundOfStop: MediaPlayer,
        binding: FragmentBlankFragmentTrainingBinding
    ) {
        timer?.cancel()
        timer = object : CountDownTimer(millisInFuture, 1) {
            override fun onTick(p0: Long) {
                if (isRestTimer) {
                    millisLeftOnRest = p0
                    val minutes = (p0 / (1000 * 60))
                    val seconds = ((p0 / 1000) - minutes * 60)
                    binding.timerTextViewRest.text = "$minutes:$seconds"
                } else {
                    millisLeft = p0
                    val minutes = (p0 / (1000 * 60))
                    val seconds = ((p0 / 1000) - minutes * 60)
                    binding.timerTextView.text = "$minutes:$seconds"
                }
            }

            override fun onFinish() {
                if (isRestTimer)
                    binding.timerTextViewRest.text = "Закончили"
                else
                    binding.timerTextView.text = "Закончили"
                if (!end) {
                    soundPlay(soundOfStop)
                    end = true
                }
                if (isRestTimer)
                    binding.linerLayoutBlankRest.isVisible = false
                binding.pauseButton.visibility = View.GONE
            }
        }
        (timer as CountDownTimer).start()
    }

    //Постановка таймера на паузу
    fun timerPause() {
        timer?.cancel()
    }

    //Воспроизведение таймера с того момента когда он остановился
    fun timerResume(
        isRestTimer: Boolean,
        binding: FragmentBlankFragmentTrainingBinding,
        soundOfStop: MediaPlayer
    ) {
        timer?.cancel()
        if (isRestTimer)
            timerStart(isRestTimer, millisStartOnRest, soundOfStop, binding)
        else
            timerStart(isRestTimer, millisLeft, soundOfStop, binding)
    }

    fun plus30Sec(
        binding: FragmentBlankFragmentTrainingBinding,
        soundOfStop: MediaPlayer
    ) {
        timer?.cancel()
        timerStart(true, millisLeftOnRest + plus30SecOnRest, soundOfStop, binding)
    }

    fun getRecommendations(recommendations: Array<String>): String {
        val randomIndex = Random().nextInt(recommendations.size)
        println("RANDOM: $randomIndex")
        return recommendations[randomIndex]
    }
}