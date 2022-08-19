package com.example.medicalrehabilitation.presenter

import android.app.AlertDialog
import android.media.MediaPlayer
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.example.medicalrehabilitation.view.TrainingActivity

interface TrainingInterface {
    fun videoPlay(videoView: VideoView)
    fun videoPause(videoView: VideoView)
    fun soundPlay(sound: MediaPlayer)
    fun soundPause(sound: MediaPlayer)
    fun aboutExercise(builder: AlertDialog.Builder)
    fun timerPause()
    fun timerResume(
        timerTextView: TextView,
        soundOfStop: MediaPlayer,
        videoView: VideoView,
        pauseButton: Button,
        mediaController: MediaController
    )

    fun timerStart(
        millisInFuture: Long,
        timerTextView: TextView,
        soundOfStop: MediaPlayer,
        videoView: VideoView,
        pauseButton: Button,
        mediaController: MediaController
    )

    fun videoChange(
        timerTextView: TextView,
        pauseButton: Button,
        videoView: VideoView,
    )

    fun returnNumberOfTraining(): Int
    fun attachView(trainingActivity: TrainingActivity)
    fun changeDescription(exerciseTextView: TextView)
}