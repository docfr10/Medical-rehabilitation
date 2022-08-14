package com.example.medicalrehabilitation.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.presenter.TrainingPresenter

//Класс, отвечающий за работу экрана тренировки
class Training : AppCompatActivity() {
    private lateinit var myVideoUri: Uri //Ссылка на видео, которое будет проигрываться
    private lateinit var videoView: VideoView //Отображение видеофайла, который выбран в Uri
    private lateinit var abouttrainimageButton: ImageButton //Кнопка с изображением "Об упражнении"
    private lateinit var nextbutton: Button //Кнопка "Следующее упражнение" переключает упражнение
    private lateinit var pausebutton: Button //Кнопка "Пауза" ставит таймер и проигрываемое видео на паузу
    private lateinit var timertextView: TextView //Текстовое поле, отображающее время на таймере
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании упражнения
    private lateinit var mediaController: MediaController //Элементы управления видео(пауза, перемотка)

    private var numberoftraining: Int = 0 //Номер упражнения
    private var trainingPresenter: TrainingPresenter = TrainingPresenter()
    private var trainingActivity: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_training)

        //Присваиваем значения в коде к значениям в разметке
        myVideoUri = Uri.parse("android.resource://$packageName/" + R.raw.video)
        videoView = findViewById(R.id.training_videoView)
        abouttrainimageButton = findViewById(R.id.abouttrain_imageButton)
        pausebutton = findViewById(R.id.pause_button)
        nextbutton = findViewById(R.id.next_button)
        timertextView = findViewById(R.id.timer_textView)
        soundOfStop = MediaPlayer.create(this, R.raw.sound_stop)
        mediaController = MediaController(this)
    }

    override fun onResume() {
        super.onResume()
        trainingPresenter.timerResume(timertextView, soundOfStop, videoView, pausebutton)
        trainingPresenter.videoPlay(mediaController, videoView, myVideoUri)
        buttonClick()
    }

    override fun onPause() {
        super.onPause()
        trainingPresenter.timerPause()
        trainingPresenter.soundPause(soundOfStop)
        trainingPresenter.videoPause(mediaController, videoView, myVideoUri)
    }

    //Метод, с помошью которого можно взаимодействовать с кнопками
    private fun buttonClick() {
        //Переход с помощью кнопки к следующему упражнению
        nextbutton.setOnClickListener {
            onPause()
            rest()
        }

        //Переход с помощью кнопки к информации об упражнении
        abouttrainimageButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            trainingPresenter.aboutExercise(numberoftraining, builder)
        }

        var ispause = false
        //Постановка видео и таймера на паузу
        pausebutton.setOnClickListener {
            ispause = if (!ispause) {
                trainingPresenter.timerPause()
                trainingPresenter.videoPause(mediaController, videoView, myVideoUri)
                pausebutton.setText(R.string.resume)
                true
            } else {
                trainingPresenter.timerResume(timertextView, soundOfStop, videoView, pausebutton)
                trainingPresenter.videoPlay(mediaController, videoView, myVideoUri)
                pausebutton.setText(R.string.pause)
                false
            }
        }
    }

    //Смена видео
    private fun videoChange(
        numberoftraining: Int,
        timertextView: TextView,
        pausebutton: Button,
        mediaController: MediaController,
        videoView: VideoView
    ) {
        when (numberoftraining) {
            0 -> {
                this.myVideoUri = Uri.parse("android.resource://$packageName/" + R.raw.video1)
                this.numberoftraining = 1
                timertextView.visibility = View.GONE
                pausebutton.visibility = View.GONE
            }
            1 -> {
                nextTraining()
            }
        }
        trainingPresenter.videoPlay(mediaController, videoView, this.myVideoUri)
    }

    //Вызов activity отдыха
    private fun rest() {
        val intent = Intent(trainingActivity, Rest::class.java)
        startActivity(intent)
        videoChange(
            numberoftraining, timertextView, pausebutton, mediaController,
            videoView
        )
    }

    //Вызов activity выбора следующей тренировки
    private fun nextTraining() {
        val intent = Intent(trainingActivity, NextTraining::class.java)
        startActivity(intent)
    }
}