package com.example.medicalrehabilitation.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color.TRANSPARENT
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.ActivityTrainingBinding
import com.example.medicalrehabilitation.presenter.TrainingPresenter

//Класс, отвечающий за работу экрана тренировки
class TrainingActivity : AppCompatActivity() {
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании упражнения
    private lateinit var mediaController: MediaController //Элементы управления видео(пауза, перемотка)

    private var trainingPresenter: TrainingPresenter = TrainingPresenter()
    private var trainingActivity: Activity = this
    private lateinit var binding: ActivityTrainingBinding

    private var isPause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        trainingPresenter.attachView(this)
        binding.exerciseTextView.text =
            getText(R.string.description1) //Текстовое поле, отображающее информацию об упражнении
        soundOfStop = MediaPlayer.create(this, R.raw.sound_stop)
        mediaController = MediaController(this)
        binding.trainingVideoView.setBackgroundColor(TRANSPARENT) //Отображение видеофайла, который выбран в Uri
    }

    override fun onResume() {
        super.onResume()
        binding.pauseButton.setOnClickListener { pauseButtonClicked() } //Кнопка "Пауза" ставит таймер и проигрываемое видео на паузу
        binding.nextButton.setOnClickListener { nextButtonClicked() } //Кнопка "Следующее упражнение" переключает упражнение
        binding.abouttrainImageButton.setOnClickListener { aboutTrainImageButtonClicked() } //Кнопка с изображением "Об упражнении"
        timerResume(
            binding.timerTextView,
            soundOfStop,
            binding.trainingVideoView,
            binding.pauseButton,
            mediaController
        )
        videoPlay(binding.trainingVideoView)
    }

    override fun onPause() {
        super.onPause()
        timerPause()
        soundPause(soundOfStop)
        videoPause(binding.trainingVideoView)
    }

    private fun pauseButtonClicked() {
        isPause = if (!isPause) {
            timerPause()
            videoPause(binding.trainingVideoView)
            binding.pauseButton.setText(R.string.resume)
            true
        } else {
            timerResume(
                binding.timerTextView,
                soundOfStop,
                binding.trainingVideoView,
                binding.pauseButton,
                mediaController
            )
            videoPlay(binding.trainingVideoView)
            binding.pauseButton.setText(R.string.pause)
            false
        }

    }

    private fun nextButtonClicked() {
        onPause()
        rest()
        videoChange()
    }

    private fun aboutTrainImageButtonClicked() {
        val builder = AlertDialog.Builder(this)
        aboutExercise(builder)
    }

    private fun videoChange() {
        trainingPresenter.videoChange(
            binding.timerTextView,
            binding.pauseButton,
            binding.trainingVideoView,
        )
        videoPlay(binding.trainingVideoView)
        Handler().postDelayed({
            trainingPresenter.changeDescription(binding.exerciseTextView)
        }, 100)
        if (trainingPresenter.returnNumberOfTraining() == 0) {
            nextTraining()
        }
    }

    private fun videoPlay(videoView: VideoView) {
        trainingPresenter.videoPlay(videoView)
    }

    private fun videoPause(videoView: VideoView) {
        trainingPresenter.videoPause(videoView)
    }

    private fun timerResume(
        timerTextView: TextView, soundOfStop: MediaPlayer, videoView: VideoView,
        pauseButton: Button, mediaController: MediaController
    ) {
        trainingPresenter.timerResume(
            timerTextView, soundOfStop, videoView, pauseButton, mediaController
        )
    }

    private fun timerPause() {
        trainingPresenter.timerPause()
    }

    private fun soundPause(soundOfStop: MediaPlayer) {
        trainingPresenter.soundPause(soundOfStop)
    }

    private fun aboutExercise(builder: AlertDialog.Builder) {
        trainingPresenter.aboutExercise(builder)
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