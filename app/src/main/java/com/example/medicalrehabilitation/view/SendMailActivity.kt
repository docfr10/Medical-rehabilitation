package com.example.medicalrehabilitation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.presenter.SendMailPresenter

//Класс, отвечающий за отправку сообщения на почту врачу
class SendMailActivity : AppCompatActivity() {
    private lateinit var doctoremail: EditText //Электронная почта врача
    private lateinit var trainingspinner: Spinner //Выбор того как прошла тренировка
    private lateinit var painfulspinner: Spinner //Выбор того было ли больно при тренировке
    private lateinit var failedexerciese: EditText //Выбор того какие упражнения не получились
    private lateinit var sendemail: Button //Кнопка отправки письма
    private var sendMailPresenter: SendMailPresenter = SendMailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mail)

        //Присваиваем значения в коде к значениям в разметке
        sendMailPresenter.attachView(this)
        doctoremail = findViewById(R.id.doctorsmail_editTextTextEmailAddress)
        trainingspinner = findViewById(R.id.training_spinner)
        painfulspinner = findViewById(R.id.painful_spinner)
        failedexerciese = findViewById(R.id.failed_editTextNumber)
        sendemail = findViewById(R.id.send_button)
    }

    override fun onResume() {
        super.onResume()
        sendMailPresenter.sendEmail(sendemail, doctoremail, trainingspinner)
    }

    fun chooseEmail(intent: Intent) {
        startActivity(Intent.createChooser(intent, "Send mail..."))
    }
}