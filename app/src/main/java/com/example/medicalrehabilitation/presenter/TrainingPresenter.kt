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
import com.example.medicalrehabilitation.view.TrainingActivity

class TrainingPresenter : TrainingInterface {

    //Ссылка на видео, которое будет проигрываться
    private var timer: CountDownTimer? = null //Таймер
    private var millisStart: Long = 20000 //120000 //Время выполнения упражнения
    private var millisLeft: Long = millisStart //Время, оставщееся до конца упражнения
    private var end: Boolean =
        false //Параметр, определяюший завершился ли таймер, необходим для повторного невоспроизведения звука завершения упражнения
    private var numberOfTraining: Int = 1 //Номер упражнения
    private var myVideoUri =
        Uri.parse("android.resource://com.example.medicalrehabilitation/" + R.raw.video1)
    private lateinit var trainingActivity: TrainingActivity

    override fun attachView(trainingActivity: TrainingActivity) {
        this.trainingActivity = trainingActivity
    }

    //Воспроизведение видео
    override fun videoPlay(
        videoView: VideoView
    ) {
        videoView.setVideoURI(myVideoUri)
        videoView.start()
        videoView.setOnPreparedListener { it.isLooping = true }
    }

    //Остановка видео
    override fun videoPause(
        videoView: VideoView,
    ) {
        videoView.setVideoURI(myVideoUri)
        videoView.pause()
    }

    //Смена видео
    override fun videoChange(
        timerTextView: TextView,
        pauseButton: Button,
        videoView: VideoView,
        exerciseTextView: TextView
    ) {
        when (numberOfTraining) {
            1 -> {
                this.myVideoUri =
                    Uri.parse(
                        "android.resource://com.example.medicalrehabilitation/"
                                + R.raw.video2
                    )
                this.numberOfTraining = 2
                exerciseTextView.text = trainingActivity.resources.getText(R.string.description2)
                timerTextView.visibility = View.GONE
                pauseButton.visibility = View.GONE
            }
            2 -> {
                this.myVideoUri =
                    Uri.parse(
                        "android.resource://com.example.medicalrehabilitation/"
                                + R.raw.video3
                    )
                this.numberOfTraining = 3
                exerciseTextView.text =
                    trainingActivity.resources.getText(R.string.description3)
            }
            3 -> {
                this.myVideoUri =
                    Uri.parse(
                        "android.resource://com.example.medicalrehabilitation/"
                                + R.raw.video4
                    )
                this.numberOfTraining = 4
                exerciseTextView.text =
                    trainingActivity.resources.getText(R.string.description4)
            }
            4 -> {
                numberOfTraining = 0
            }
        }
        videoPlay(videoView)
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
        when (numberOfTraining) {
            1 -> builder.setMessage(R.string.description1)
            2 -> builder.setMessage("Описание второго упражнения")
        }
        builder.setTitle((R.string.about_exercise))
            .setPositiveButton((R.string.clear)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }

    //Запуск и проверка таймера на окончание
    override fun timerStart(
        millisInFuture: Long,
        timerTextView: TextView,
        soundOfStop: MediaPlayer,
        videoView: VideoView,
        pauseButton: Button,
        mediaController: MediaController
    ) {
        timer?.cancel()
        timer = object : CountDownTimer(millisInFuture, 1) {
            override fun onTick(p0: Long) {
                millisLeft = p0
                val minutes = (p0 / (1000 * 60))
                val seconds = ((p0 / 1000) - minutes * 60)
                timerTextView.text = "$minutes:$seconds"
            }

            override fun onFinish() {
                timerTextView.text = "Закончили"
                if (!end) {
                    soundPlay(soundOfStop)
                    end = true
                }
                pauseButton.visibility = View.GONE
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
        timerTextView: TextView,
        soundOfStop: MediaPlayer,
        videoView: VideoView,
        pauseButton: Button,
        mediaController: MediaController
    ) {
        timer?.cancel()
        timerStart(millisLeft, timerTextView, soundOfStop, videoView, pauseButton, mediaController)
    }

    override fun returnNumberOfTraining(): Int {
        return numberOfTraining
    }
}