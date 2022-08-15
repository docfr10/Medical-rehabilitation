package com.example.medicalrehabilitation.presenter

import android.app.AlertDialog
import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.example.medicalrehabilitation.R

class TrainingPresenter : TrainingInterface {

    //Ссылка на видео, которое будет проигрываться
    private var timer: CountDownTimer? = null //Таймер
    private var millisStart: Long = 20000 //120000 //Время выполнения упражнения
    private var millisLeft: Long = millisStart //Время, оставщееся до конца упражнения
    private var end: Boolean =
        false //Параметр, определяюший завершился ли таймер, необходим для повторного невоспроизведения звука завершения упражнения
    private var numberoftraining: Int = 1 //Номер упражнения
    private var myVideoUri =
        Uri.parse("android.resource://com.example.medicalrehabilitation/" + R.raw.video)

    //Воспроизведение видео
    override fun videoPlay(
        mediaController: MediaController,
        videoView: VideoView,
    ) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()
        videoView.setOnPreparedListener { it.isLooping = true }
    }

    //Остановка видео
    override fun videoPause(
        mediaController: MediaController,
        videoView: VideoView,
    ) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.pause()
    }

    //Смена видео
    override fun videoChange(
        timertextView: TextView,
        pausebutton: Button,
        mediaController: MediaController,
        videoView: VideoView
    ) {
        when (numberoftraining) {
            1 -> {
                this.myVideoUri =
                    Uri.parse(
                        "android.resource://com.example.medicalrehabilitation/"
                                + R.raw.video1
                    )
                this.numberoftraining = 2
                timertextView.visibility = View.GONE
                pausebutton.visibility = View.GONE
            }
            2 -> {
                numberoftraining = 0
            }
        }
        videoPlay(mediaController, videoView)
    }

    //Проигрывание звука
    override fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }

    //Остановка звука
    override fun soundPause(sound: MediaPlayer) {
        sound.pause()
    }

    //Смена информации об упражнении, реализована в виде диалогового окна
    override fun aboutExercise(builder: AlertDialog.Builder) {
        when (numberoftraining) {
            0 -> builder.setMessage(R.string.description0)
            1 -> builder.setMessage("Описание второго упражнения")
        }
        builder.setTitle((R.string.about_exercise))
            .setPositiveButton((R.string.clear)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }

    //Запуск и проверка таймера на окончание
    override fun timerStart(
        millisInFuture: Long,
        timertextView: TextView,
        soundOfStop: MediaPlayer,
        videoView: VideoView,
        pausebutton: Button
    ) {
        timer?.cancel()
        timer = object : CountDownTimer(millisInFuture, 1) {
            override fun onTick(p0: Long) {
                millisLeft = p0
                val minutes = (p0 / (1000 * 60))
                val seconds = ((p0 / 1000) - minutes * 60)
                timertextView.text = "$minutes:$seconds"
            }

            override fun onFinish() {
                timertextView.text = "Закончили"
                if (!end) {
                    soundPlay(soundOfStop)
                    end = true
                }
                videoView.stopPlayback()
                pausebutton.visibility = View.GONE
            }
        }
        (timer as CountDownTimer).start()
    }

    //Постановка таймера на паузу
    override fun timerPause() {
        timer?.cancel()
    }

    //Воспроизведение таймера с того момента когда он остановился
    override fun timerResume(
        timertextView: TextView,
        soundOfStop: MediaPlayer,
        videoView: VideoView,
        pausebutton: Button
    ) {
        timer?.cancel()
        timerStart(millisLeft, timertextView, soundOfStop, videoView, pausebutton)
    }

    override fun returnNumberOfTraining(): Int {
        return numberoftraining
    }
}