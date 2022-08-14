package com.example.medicalrehabilitation.view

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.*
import com.example.medicalrehabilitation.databinding.ActivityNextTrainingBinding
import java.util.*

//Класс, отвечающий за выбор даты следующей тренировки
class NextTraining : AppCompatActivity() {

    private lateinit var binding: ActivityNextTrainingBinding //Библиотека binding
                                                              // упрощает работу с компонентами GUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Убирается панель действий сверху
        window.requestFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = ActivityNextTrainingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        createNotificationChannel()
        //С помощью binding обрабатываем нажатия на кнопку
        binding.button.setOnClickListener { createNotifications() }
    }

    //Метод, отвечающий за создание уведомлений на панели действий
    private fun createNotifications() {
        val intent = Intent(applicationContext, Notifications::class.java)
        val title = this.getString(R.string.notification) //Название уведомления
        val message = this.getString(R.string.training_time) //Текст уведомления
        //Передаем название и текст уведомления в Notifications
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        //Создаем широковещательный сигнал для отправки уведомления
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                applicationContext,
                notificationID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            TODO("VERSION.SDK_INT < M")
        }

        //Записываем дату когда необходимо отправить уведомление
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time)
    }

    //Метод, отвечающий за показ пользователю того когда будет отправлено уведомление
    private fun showAlert(time: Long) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)
        val intent1 = Intent(this@NextTraining, SendMail::class.java)

        AlertDialog.Builder(this)
            .setTitle(R.string.notification_scheduled)
            .setMessage(
                dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton(R.string.clear) { _, _ -> startActivity(intent1) }
            .show()
    }

    //Метод, переводящий выбранные значения даты и времени в миллисекунды
    private fun getTime(): Long {
        val minute = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.timePicker.minute
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        //Возвращает миллисекунды чтобы через время отправить уведомление
        return calendar.timeInMillis
    }

    //Метод, отвечающий за создание канала уведомлений
    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_DEFAULT
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(channelID, name, importance)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}