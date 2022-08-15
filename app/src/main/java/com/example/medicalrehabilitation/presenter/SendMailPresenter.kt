package com.example.medicalrehabilitation.presenter

import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.medicalrehabilitation.view.SendMailActivity

class SendMailPresenter : SendMailInterface {

    private lateinit var sendMail: SendMailActivity

    override fun attachView(sendMail: SendMailActivity) {
        this.sendMail = sendMail
    }

    override fun sendEmail(
        sendemail: Button,
        doctoremail: EditText,
        trainingspinner: Spinner,
        theme: String
    ) {
        sendemail.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("mailto:${doctoremail.text}")
            ) //Кому отправляем
            intent.putExtra(Intent.EXTRA_SUBJECT, theme) //Тема письма
            intent.putExtra(Intent.EXTRA_TEXT, "Здесь содержится текст письма")
            sendMail.chooseEmail(intent)
        }
    }
}