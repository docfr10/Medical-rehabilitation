package com.example.medicalrehabilitation.presenter

import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.medicalrehabilitation.view.SendMailActivity

interface SendMailInterface {
    fun attachView(sendMail: SendMailActivity)
    fun sendEmail(
        sendEmail: Button,
        doctorEmail: EditText,
        trainingSpinner: Spinner,
        painfulSpinner: Spinner,
        failedExercises: EditText,
        theme: String
    )
}