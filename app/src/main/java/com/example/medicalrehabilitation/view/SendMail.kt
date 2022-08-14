package com.example.medicalrehabilitation.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.medicalrehabilitation.R

//Класс, отвечающий за отправку сообщения на почту врачу
class SendMail : AppCompatActivity() {
    private lateinit var doctoremail: EditText //Электронная почта врача
    private lateinit var trainingspinner: Spinner //Выбор того как прошла тренировка
    private lateinit var painfulspinner: Spinner //Выбор того было ли больно при тренировке
    private lateinit var failedexerciese: EditText //Выбор того какие упражнения не получились
    private lateinit var sendemail: Button //Кнопка отправки письма

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mail)

        //Присваиваем значения в коде к значениям в разметке
        doctoremail = findViewById(R.id.doctorsmail_editTextTextEmailAddress)
        trainingspinner = findViewById(R.id.training_spinner)
        painfulspinner = findViewById(R.id.painful_spinner)
        failedexerciese = findViewById(R.id.failed_editTextNumber)
        sendemail = findViewById(R.id.send_button)
    }

    override fun onResume() {
        super.onResume()
        sendEmail()
    }

    private fun sendEmail() {
        sendemail.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:${doctoremail.text}"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Реабилитация")
            intent.putExtra(Intent.EXTRA_SUBJECT, trainingspinner.toString())
            startActivity(Intent.createChooser(intent, "Send mail..."))
        }
    }
}