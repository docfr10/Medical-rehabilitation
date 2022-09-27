package com.example.medicalrehabilitation.viewmodel

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicalrehabilitation.*
import com.example.medicalrehabilitation.databinding.ActivityNextTrainingBinding
import java.text.DateFormat
import java.util.*

class NextTrainingViewModel : ViewModel() {
    var showAlert = MutableLiveData<Boolean>()

    //Метод, отвечающий за создание уведомлений на панели действий
    fun createNotifications(
        intent: Intent,
        applicationContext: Context,
        alarmManager: AlarmManager,
        time: Long,
        title: String,
        message: String
    ) {
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

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    //Метод, переводящий выбранные значения даты и времени в миллисекунды
    fun getTime(binding: ActivityNextTrainingBinding): Long {
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

    //Метод, отвечающий за показ пользователю того когда будет отправлено уведомление
    fun showAlert(
        date: Date,
        dateFormat: DateFormat,
        timeFormat: DateFormat,
        builder: AlertDialog.Builder
    ) {
        builder
            .setTitle(R.string.notification_scheduled)
            .setMessage(
                dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton(R.string.clear) { _, _ -> showAlert.value = true }
            .show()
    }

    //Метод, отвечающий за создание канала уведомлений
    fun createNotificationChannel(
        notificationManager: NotificationManager
    ) {
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
        notificationManager.createNotificationChannel(channel)
    }

    fun returnShowAlert(): Boolean? {
        Log.d("returnShowAlert", showAlert.value.toString())
        return showAlert.value
    }
}