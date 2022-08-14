package com.example.medicalrehabilitation.presenter

import android.app.AlertDialog
import android.media.MediaPlayer
import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.view.TrainingInterface

class TrainingPresenter : TrainingInterface {

    //Воспроизведение видео
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

    //Остановка видео
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

    //Проигрывание звука
    override fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }

    //Остановка звука
    override fun soundPause(sound: MediaPlayer) {
        sound.pause()
    }

    //Смена информации об упражнении, реализована в виде диалогового окна
    override fun aboutExercise(numberoftraining: Int, builder: AlertDialog.Builder) {
        when (numberoftraining) {
            0 -> builder.setMessage(R.string.description0)
            1 -> builder.setMessage("Описание второго упражнения")
        }
        builder.setTitle((R.string.about_exercise))
            .setPositiveButton((R.string.clear)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }

}