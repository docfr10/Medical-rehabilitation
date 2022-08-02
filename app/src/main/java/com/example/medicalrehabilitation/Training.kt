package com.example.medicalrehabilitation

import android.app.AlertDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class Training : AppCompatActivity() {

    private lateinit var myVideoUri: Uri
    private lateinit var videoView: VideoView
    private lateinit var abouttrainimageButton: ImageButton
    private lateinit var nextbutton: Button
    private var numberoftraining: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        val mediaController = MediaController(this)
        myVideoUri = Uri.parse("android.resource://$packageName/" + R.raw.video)
        videoView = findViewById(R.id.training_videoView)
        abouttrainimageButton = findViewById(R.id.abouttrain_imageButton)
        nextbutton = findViewById(R.id.next_button)

        play(videoView, myVideoUri, mediaController)

        //Переход с помощью кнопки к следующему упражнению
        nextbutton.setOnClickListener {
            if (myVideoUri == Uri.parse("android.resource://$packageName/" + R.raw.video)) {
                myVideoUri = Uri.parse("android.resource://$packageName/" + R.raw.video1)
                numberoftraining = 1
            }
            play(videoView, myVideoUri, mediaController)
        }

        //Переход с помощью кнопки к информации об упражнении
        abouttrainimageButton.setOnClickListener {
            window()
        }
    }

    private fun play(videoView: VideoView, myVideoUri: Uri, mediaController: MediaController) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()
        videoView.setOnPreparedListener { it.isLooping = true }
    }

    private fun window() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.about_exercise))
        if (numberoftraining == 0) {
            builder.setMessage(R.string.description0)
        } else if (numberoftraining == 1) {
            builder.setMessage("!!!Описание упражнения!!!")
        }
        builder.setPositiveButton(getString(R.string.clear)) { dialog, id ->
            dialog.cancel()
        }
        builder.create()
        builder.show()
    }
}