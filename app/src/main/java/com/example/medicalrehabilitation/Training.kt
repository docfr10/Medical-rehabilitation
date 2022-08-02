package com.example.medicalrehabilitation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.widget.Button
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.DialogFragment

class Training : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        var myVideoUri: Uri = Uri.parse("android.resource://$packageName/" + R.raw.video)
        val videoView: VideoView = findViewById(R.id.training_videoView)
        val mediaController = MediaController(this)
        val abouttrainimageButton: ImageButton = findViewById(R.id.abouttrain_imageButton)
        val nextbutton: Button = findViewById(R.id.next_button)

        play(videoView, myVideoUri, mediaController)

        //Переход с помощью кнопки к следующему упражнению
        nextbutton.setOnClickListener {
            if (myVideoUri == Uri.parse("android.resource://$packageName/" + R.raw.video)) {
                myVideoUri = Uri.parse("android.resource://$packageName/" + R.raw.video1)
                play(videoView, myVideoUri, mediaController)
            }
        }

        //Переход с помощью кнопки к информации об упражнении
        abouttrainimageButton.setOnClickListener {
            val myDialogFragment = MyDialogFragment()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
        }
    }

    private fun play(videoView: VideoView, myVideoUri: Uri, mediaController: MediaController) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()
        videoView.setOnPreparedListener { it.isLooping = true }
    }
}

class MyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.about_exercise))
                .setMessage("!!!Описание упражнения!!!")
                .setPositiveButton(getString(R.string.clear)) { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
