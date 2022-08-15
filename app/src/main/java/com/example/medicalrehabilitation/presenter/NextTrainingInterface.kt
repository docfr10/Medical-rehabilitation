package com.example.medicalrehabilitation.presenter

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import com.example.medicalrehabilitation.databinding.ActivityNextTrainingBinding
import com.example.medicalrehabilitation.view.NextTraining
import java.text.DateFormat
import java.util.*


interface NextTrainingInterface {
    fun createNotifications(
        intent: Intent,
        applicationContext: Context,
        alarmManager: AlarmManager,
        time: Long
    )

    fun getTime(binding: ActivityNextTrainingBinding): Long
    fun createNotificationChannel(
        binding: ActivityNextTrainingBinding,
        notificationManager: NotificationManager
    )

    fun attachView(nextTraining: NextTraining)
    fun showAlert(
        time: Long,
        date: Date,
        dateFormat: DateFormat,
        timeFormat: DateFormat,
        builder: AlertDialog.Builder
    )
}