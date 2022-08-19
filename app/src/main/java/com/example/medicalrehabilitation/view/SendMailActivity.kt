package com.example.medicalrehabilitation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.medicalrehabilitation.MainActivity
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.presenter.SendMailPresenter

//Класс, отвечающий за отправку сообщения на почту врачу
class SendMailActivity : AppCompatActivity() {
    private lateinit var doctorEmail: EditText //Электронная почта врача
    private lateinit var trainingSpinner: Spinner //Выбор того как прошла тренировка
    private lateinit var painfulSpinner: Spinner //Выбор того было ли больно при тренировке
    private lateinit var failedExercises: EditText //Выбор того какие упражнения не получились
    private lateinit var sendEmail: Button //Кнопка отправки письма
    private var sendMailPresenter: SendMailPresenter = SendMailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mail)

        //Присваиваем значения в коде к значениям в разметке
        sendMailPresenter.attachView(this)
        doctorEmail = findViewById(R.id.doctorsmail_editTextTextEmailAddress)
        trainingSpinner = findViewById(R.id.training_spinner)
        painfulSpinner = findViewById(R.id.painful_spinner)
        failedExercises = findViewById(R.id.failed_editTextTextMultiLine)
        sendEmail = findViewById(R.id.send_button)
    }

    override fun onResume() {
        super.onResume()
        sendMailPresenter.sendEmail(
            sendEmail,
            doctorEmail,
            trainingSpinner,
            painfulSpinner,
            failedExercises,
            getText(R.string.app_name) as String
        )
    }
    
    override fun onRestart() {
        super.onRestart()
        transitionToMainActivity()
    }

    fun chooseEmail(intent: Intent) {
        startActivity(Intent.createChooser(intent, getText(R.string.send_mail)))
    }

    private fun transitionToMainActivity() {
        val intent = Intent(this@SendMailActivity, MainActivity::class.java)
        startActivity(intent)
    }
}