package com.example.medicalrehabilitation.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color.TRANSPARENT
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.presenter.TrainingPresenter

//Класс, отвечающий за работу экрана тренировки
class TrainingActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView //Отображение видеофайла, который выбран в Uri
    private lateinit var aboutTrainImageButton: ImageButton //Кнопка с изображением "Об упражнении"
    private lateinit var nextButton: Button //Кнопка "Следующее упражнение" переключает упражнение
    private lateinit var pauseButton: Button //Кнопка "Пауза" ставит таймер и проигрываемое видео на паузу
    private lateinit var timerTextView: TextView //Текстовое поле, отображающее время на таймере
    private lateinit var exerciseTextView: TextView //Текстовое поле, отображающее информацию об упражнении
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании упражнения
    private lateinit var mediaController: MediaController //Элементы управления видео(пауза, перемотка)

    private var trainingPresenter: TrainingPresenter = TrainingPresenter()
    private var trainingActivity: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_training)

        //Присваиваем значения в коде к значениям в разметке
        trainingPresenter.attachView(this)
        videoView = findViewById(R.id.training_videoView)
        aboutTrainImageButton = findViewById(R.id.abouttrain_imageButton)
        pauseButton = findViewById(R.id.pause_button)
        nextButton = findViewById(R.id.next_button)
        timerTextView = findViewById(R.id.timer_textView)
        exerciseTextView = findViewById(R.id.exercise_textView)
        exerciseTextView.text = getText(R.string.description1)
        soundOfStop = MediaPlayer.create(this, R.raw.sound_stop)
        mediaController = MediaController(this)
        videoView.setBackgroundColor(TRANSPARENT)
    }

    override fun onResume() {
        super.onResume()
        trainingPresenter.timerResume(
            timerTextView,
            soundOfStop,
            videoView,
            pauseButton,
            mediaController
        )
        trainingPresenter.videoPlay(mediaController, videoView)
        buttonClick()
    }

    override fun onPause() {
        super.onPause()
        trainingPresenter.timerPause()
        trainingPresenter.soundPause(soundOfStop)
        trainingPresenter.videoPause(mediaController, videoView)
    }

    //Метод, с помошью которого можно взаимодействовать с кнопками
    private fun buttonClick() {
        //Переход с помощью кнопки к следующему упражнению
        nextButton.setOnClickListener {
            onPause()
            rest()
            videoChange()
        }

        //Переход с помощью кнопки к информации об упражнении
        aboutTrainImageButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            trainingPresenter.aboutExercise(builder)
        }

        var isPause = false
        //Постановка видео и таймера на паузу
        pauseButton.setOnClickListener {
            isPause = if (!isPause) {
                trainingPresenter.timerPause()
                trainingPresenter.videoPause(mediaController, videoView)
                pauseButton.setText(R.string.resume)
                true
            } else {
                trainingPresenter.timerResume(
                    timerTextView,
                    soundOfStop,
                    videoView,
                    pauseButton,
                    mediaController
                )
                trainingPresenter.videoPlay(mediaController, videoView)
                pauseButton.setText(R.string.pause)
                false
            }
        }
    }

    private fun videoChange() {
        trainingPresenter.videoChange(
            timerTextView,
            pauseButton,
            mediaController,
            videoView,
            exerciseTextView
        )
        trainingPresenter.videoPlay(mediaController, videoView)
        if (trainingPresenter.returnNumberOfTraining() == 0) {
            nextTraining()
        }
    }

    //Вызов activity отдыха
    private fun rest() {
        val intent = Intent(trainingActivity, RestActivity::class.java)
        startActivity(intent)
    }

    //Вызов activity выбора следующей тренировки
    private fun nextTraining() {
        val intent = Intent(trainingActivity, NextTrainingActivity::class.java)
        startActivity(intent)
    }
}