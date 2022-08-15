package com.example.medicalrehabilitation.presenter

import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.medicalrehabilitation.view.SendMailActivity

interface SendMailInterface {
    fun sendEmail(sendemail: Button, doctoremail: EditText, trainingspinner: Spinner)
    fun attachView(sendMail: SendMailActivity)
}