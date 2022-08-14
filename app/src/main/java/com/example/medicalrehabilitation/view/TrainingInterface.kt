package com.example.medicalrehabilitation.view

import android.app.AlertDialog
import android.media.MediaPlayer
import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView

interface TrainingInterface {
    fun videoPlay(mediaController: MediaController, videoView: VideoView, myVideoUri: Uri)
    fun videoPause(mediaController: MediaController, videoView: VideoView, myVideoUri: Uri)
    fun soundPlay(sound: MediaPlayer)
    fun soundPause(sound: MediaPlayer)
    fun aboutExercise(numberoftraining: Int, builder: AlertDialog.Builder)
}