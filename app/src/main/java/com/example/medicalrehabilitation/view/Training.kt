package com.example.medicalrehabilitation.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
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
    private var timer: CountDownTimer? = null //Таймер
    private var millisStart: Long = 20000; //120000 //Время выполнения упражнения
    private var millisLeft: Long = millisStart //Время, оставщееся до конца упражнения
    private var end: Boolean = false //Параметр, определяюший завершился ли таймер,
    // необходим для повторного невоспроизведения звука завершения упражнения

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
        timerResume()
        videoPlay(mediaController, videoView, myVideoUri)
        buttonClick()
    }

    override fun onPause() {
        super.onPause()
        timerPause()
        soundPause(soundOfStop)
        videoPause(mediaController, videoView, myVideoUri)
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
            TrainingPresenter().aboutExercise(numberoftraining, builder)
        }

        var ispause: Boolean = false
        //Постановка видео и таймера на паузу
        pausebutton.setOnClickListener {
            ispause = if (!ispause) {
                timerPause()
                videoPause(mediaController, videoView, myVideoUri)
                pausebutton.setText(R.string.resume)
                true
            } else {
                timerResume()
                videoPlay(mediaController, videoView, myVideoUri)
                pausebutton.setText(R.string.pause)
                false
            }
        }
    }

    //Воспроизведение видео
    private fun videoPlay(
        mediaController: MediaController,
        videoView: VideoView,
        myVideoUri: Uri
    ) {
        trainingPresenter.videoPlay(mediaController, videoView, myVideoUri)
    }


    //Остановка видео
    private fun videoPause(
        mediaController: MediaController,
        videoView: VideoView,
        myVideoUri: Uri
    ) {
        trainingPresenter.videoPause(mediaController, videoView, myVideoUri)
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


    //Запуск и проверка таймера на окончание
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
                if (!end) {
                    soundPlay(soundOfStop)
                    end = true
                }
                videoView.stopPlayback()
                pausebutton.visibility = View.GONE
            }
        }
        (timer as CountDownTimer).start()
    }

    //Постановка таймера на паузу
    private fun timerPause() {
        timer?.cancel()
    }

    //Воспроизведение таймера с того момента когда он остановился
    private fun timerResume() {
        timerStart(millisLeft);
    }

    //Проигрывание звука
    private fun soundPlay(sound: MediaPlayer) {
        trainingPresenter.soundPlay(sound)
    }

    //Остановка звука
    private fun soundPause(sound: MediaPlayer) {
        trainingPresenter.soundPause(sound)
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