package com.example.medicalrehabilitation.presenter

import android.app.AlertDialog
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView

interface TrainingInterface {
    fun videoPlay(mediaController: MediaController, videoView: VideoView)
    fun videoPause(mediaController: MediaController, videoView: VideoView)
    fun soundPlay(sound: MediaPlayer)
    fun soundPause(sound: MediaPlayer)
    fun aboutExercise(builder: AlertDialog.Builder)
    fun timerPause()
    fun timerResume(
        timertextView: TextView,
        soundOfStop: MediaPlayer,
        videoView: VideoView,
        pausebutton: Button
    )

    fun timerStart(
        millisInFuture: Long,
        timertextView: TextView,
        soundOfStop: MediaPlayer,
        videoView: VideoView,
        pausebutton: Button
    )

    fun videoChange(
        timertextView: TextView,
        pausebutton: Button,
        mediaController: MediaController,
        videoView: VideoView
    )

    fun returnNumberOfTraining(): Int
}