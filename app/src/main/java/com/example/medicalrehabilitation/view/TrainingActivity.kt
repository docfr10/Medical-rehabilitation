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
import androidx.lifecycle.ViewModelProvider
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.ActivityTrainingBinding
import com.example.medicalrehabilitation.viewmodel.TrainingViewModel

//Класс, отвечающий за работу экрана тренировки
class TrainingActivity : AppCompatActivity() {
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании упражнения
    private lateinit var mediaController: MediaController //Элементы управления видео(пауза, перемотка)

    private var trainingActivity: Activity = this
    private lateinit var binding: ActivityTrainingBinding

    private var isPause = false

    private lateinit var viewModel: TrainingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val provider = ViewModelProvider(this)
        viewModel = provider.get(TrainingViewModel::class.java)

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
        timerResume(binding, soundOfStop)
        videoPlay(binding)
    }

    override fun onPause() {
        super.onPause()
        timerPause()
        soundPause(soundOfStop)
        videoPause(binding)
    }

    private fun pauseButtonClicked() {
        isPause = if (!isPause) {
            timerPause()
            videoPause(binding)
            binding.pauseButton.setText(R.string.resume)
            true
        } else {
            timerResume(binding, soundOfStop)
            videoPlay(binding)
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
        viewModel.videoChange(binding)
        videoPlay(binding)
        Handler().postDelayed({
            changeDescription()
        }, 100)
        if (viewModel.returnNumberOfTraining() == 0) {
            nextTraining()
        }
    }

    private fun changeDescription() {
        viewModel.changeDescription(binding)
    }

    private fun videoPlay(binding: ActivityTrainingBinding) {
        viewModel.videoPlay(binding)
    }

    private fun videoPause(binding: ActivityTrainingBinding) {
        viewModel.videoPause(binding)
    }

    private fun timerResume(binding: ActivityTrainingBinding, soundOfStop: MediaPlayer) {
        viewModel.timerResume(binding, soundOfStop)
    }

    private fun timerPause() {
        viewModel.timerPause()
    }

    private fun soundPause(soundOfStop: MediaPlayer) {
        viewModel.soundPause(soundOfStop)
    }

    private fun aboutExercise(builder: AlertDialog.Builder) {
        viewModel.aboutExercise(builder)
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