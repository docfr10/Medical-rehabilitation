package com.example.medicalrehabilitation.presenter

import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.example.medicalrehabilitation.view.Training
import com.example.medicalrehabilitation.view.TrainingInterface

class TrainingPresenter : TrainingInterface {
    override fun videoPlay(
        mediaController: MediaController,
        videoView: VideoView,
        myVideoUri: Uri
    ) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()
        videoView.setOnPreparedListener { it.isLooping = true }
    }

    override fun videoPause(
        mediaController: MediaController,
        videoView: VideoView,
        myVideoUri: Uri
    ) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.pause()
    }


    override fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }

    override fun soundPause(sound: MediaPlayer) {
        sound.pause()
    }
}