package com.example.medicalrehabilitation

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

//Класс, отвечающий за работу TrainingActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Убирается панель действий сверху
        window.requestFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

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
        videoPlay(mediaController)
        buttonClick()
    }

    override fun onPause() {
        super.onPause()
        timerPause()
        soundPause(soundOfStop)
        videoPause(mediaController)
    }

    //Метод, с помошью которого можно взаимодействовать с кнопками
    private fun buttonClick() {
        //Переход с помощью кнопки к следующему упражнению
        nextbutton.setOnClickListener {
            onPause()
            val builder = AlertDialog.Builder(this)
                .setMessage(R.string.want_to_rest)
                .setTitle(R.string.rest)
                .setNegativeButton(getString(R.string.positive)) { dialog, id -> rest() }
                .setPositiveButton(getString(R.string.negative)) { dialog, id -> videoChange() }
                .create()
                .show()
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
                videoPause(mediaController)
                pausebutton.setText(R.string.resume)
                true
            } else {
                timerResume()
                videoPlay(mediaController)
                pausebutton.setText(R.string.pause)
                false
            }
        }
    }

    //Воспроизведение видео
    private fun videoPlay(mediaController: MediaController) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()
        videoView.setOnPreparedListener { it.isLooping = true }
    }

    //Остановка видео
    private fun videoPause(mediaController: MediaController) {
        videoView.setVideoURI(myVideoUri)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.pause()
    }

    //Смена видео
    private fun videoChange() {
        if (myVideoUri == Uri.parse("android.resource://$packageName/" + R.raw.video)) {
            myVideoUri = Uri.parse("android.resource://$packageName/" + R.raw.video1)
            numberoftraining = 1
            timertextView.visibility = View.GONE
            pausebutton.visibility = View.GONE
        } else if (myVideoUri == Uri.parse("android.resource://$packageName/" + R.raw.video1)) {
            nextTraining()
        }
        videoPlay(mediaController)
    }

    //Смена информации об упражнении, реализована в виде диалогового окна
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
        sound.start()
    }

    //Остановка звука
    private fun soundPause(sound: MediaPlayer) {
        sound.pause()
    }

    //Вызов activity отдыха
    private fun rest() {
        val intent = Intent(this@Training, Rest::class.java)
        startActivity(intent)
    }

    private fun nextTraining() {
        val intent = Intent(this@Training, NextTraining::class.java)
        startActivity(intent)
    }
}