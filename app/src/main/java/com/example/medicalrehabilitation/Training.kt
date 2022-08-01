package com.example.medicalrehabilitation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class Training : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        val videoView: VideoView = findViewById(R.id.videoView)
        val myVideoUri: Uri = Uri.parse("android.resource://$packageName/" + R.raw.video)
        videoView.setVideoURI(myVideoUri)

        val mediaController: MediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
    }
}