package com.example.medicalrehabilitation

import android.app.AlertDialog
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class Training : AppCompatActivity() {

    private lateinit var myVideoUri: Uri
    private lateinit var videoView: VideoView
    private lateinit var abouttrainimageButton: ImageButton
    private lateinit var nextbutton: Button
    private lateinit var pausebutton: Button
    private lateinit var timertextView: TextView
    private lateinit var soundStop: MediaPlayer
    private lateinit var mediaController: MediaController
    private var numberoftraining: Int = 0
    private var timer: CountDownTimer? = null
    private var millisStart: Long = 2000; //120000
    private var millisLeft: Long = millisStart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        myVideoUri = Uri.parse("android.resource://$packageName/" + R.raw.video)
        videoView = findViewById(R.id.training_videoView)
        abouttrainimageButton = findViewById(R.id.abouttrain_imageButton)
        pausebutton = findViewById(R.id.pause_button)
        nextbutton = findViewById(R.id.next_button)
        timertextView = findViewById(R.id.timer_textView)
        soundStop = MediaPlayer.create(this, R.raw.sound_stop)
        mediaController = MediaController(this)

        playVideo(mediaController)
        timerStart(millisStart)
        buttonClick()
    }

    private fun buttonClick() {
        //Переход с помощью кнопки к следующему упражнению
        nextbutton.setOnClickListener {
            if (myVideoUri == Uri.parse("android.resource://$packageName/" + R.raw.video)) {
                myVideoUri = Uri.parse("android.resource://$packageName/" + R.raw.video1)
                numberoftraining = 1
                timertextView.visibility = View.GONE
                pausebutton.visibility = View.GONE
            } else if (myVideoUri == Uri.parse("android.resource://$packageName/" + R.raw.video1)) {
                //videoView.pause()
            }
            playVideo(mediaController)
        }

        //Переход с помощью кнопки к информации об упражнении
        abouttrainimageButton.setOnClickListener {
            aboutexercise()
        }

        var ispause: Boolean = false
        //Постановка видео и таймера на паузу
        pausebutton.setOnClickListener {
            ispause = if (!ispause) {
                timerPause()
                pauseVideo(mediaController)
                pausebutton.setText(R.string.resume)
                true
            } else {
                timerResume()
                playVideo(mediaController)
                pausebutton.setText(R.string.pause)
                false
            }
        }
    }

    private fun playVideo(mediaController: MediaController) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()
        videoView.setOnPreparedListener { it.isLooping = true }
    }

    private fun pauseVideo(mediaController: MediaController) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.pause()
    }

    private fun aboutexercise() {
        val builder = AlertDialog.Builder(this)
        when (numberoftraining) {
            0 -> builder.setMessage(R.string.description0)
            1 -> builder.setMessage("Описание второго упражнения")
        }
        builder.setTitle(getString(R.string.about_exercise))
            .setPositiveButton(getString(R.string.clear)) { dialog, id -> dialog.cancel() }
            .create()
            .show()
    }

    private fun timerStart(millisInFuture: Long) {
        timer = object : CountDownTimer(millisInFuture, 1) {
            override fun onTick(p0: Long) {
                millisLeft = p0
                val minutes = (p0 / (1000 * 60));
                val seconds = ((p0 / 1000) - minutes * 60);
                timertextView.text = "$minutes:$seconds"
            }

            override fun onFinish() {
                timertextView.text = "Закончили"
                soundPlay(soundStop)
                videoView.stopPlayback()
            }
        }
        (timer as CountDownTimer).start()
    }

    private fun timerPause() {
        timer?.cancel()
    }

    private fun timerResume() {
        timerStart(millisLeft);
    }

    private fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }
}