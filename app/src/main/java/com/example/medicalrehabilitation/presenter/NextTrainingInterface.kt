package com.example.medicalrehabilitation.presenter

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import com.example.medicalrehabilitation.databinding.ActivityNextTrainingBinding


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
}