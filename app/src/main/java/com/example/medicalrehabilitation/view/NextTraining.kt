package com.example.medicalrehabilitation.view

import android.app.*
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.Notifications
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.ActivityNextTrainingBinding
import com.example.medicalrehabilitation.presenter.NextTrainingPresenter
import java.util.*

//Класс, отвечающий за выбор даты следующей тренировки
class NextTraining : AppCompatActivity() {

    private lateinit var binding: ActivityNextTrainingBinding //Библиотека binding упрощает работу с компонентами GUI
    private var nextTrainingPresenter: NextTrainingPresenter = NextTrainingPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNextTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
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

    //Метод, отвечающий за показ пользователю того когда будет отправлено уведомление
    private fun showAlert() {
        val time = nextTrainingPresenter.getTime(binding)
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle(R.string.notification_scheduled)
            .setMessage(
                dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton(R.string.clear) { _, _ -> sendMail() }
            .show()
    }

    private fun sendMail() {
        val intent1 = Intent(this@NextTraining, SendMail::class.java)
        startActivity(intent1)
    }
}