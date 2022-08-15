package com.example.medicalrehabilitation.view

import android.app.*
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.Notifications
import com.example.medicalrehabilitation.databinding.ActivityNextTrainingBinding
import com.example.medicalrehabilitation.presenter.NextTrainingPresenter
import java.util.*

//Класс, отвечающий за выбор даты следующей тренировки
class NextTrainingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNextTrainingBinding //Библиотека binding упрощает работу с компонентами GUI
    private var nextTrainingPresenter: NextTrainingPresenter = NextTrainingPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNextTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
        nextTrainingPresenter.attachView(this)
        //С помощью binding обрабатываем нажатия на кнопку
        binding.button.setOnClickListener { createNotifications() }
    }

    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nextTrainingPresenter.createNotificationChannel(binding, notificationManager)
    }

    private fun createNotifications() {
        val intent = Intent(applicationContext, Notifications::class.java)
        //Записываем дату когда необходимо отправить уведомление
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val time = nextTrainingPresenter.getTime(binding)
        nextTrainingPresenter.createNotifications(intent, applicationContext, alarmManager, time)
        showAlert()
    }

    private fun showAlert() {
        val time = nextTrainingPresenter.getTime(binding)
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)
        val builder = AlertDialog.Builder(this)
        nextTrainingPresenter.showAlert(time, date, dateFormat, timeFormat, builder)
    }

    fun sendMail() {
        val intent1 = Intent(this@NextTrainingActivity, SendMailActivity::class.java)
        startActivity(intent1)
    }
}