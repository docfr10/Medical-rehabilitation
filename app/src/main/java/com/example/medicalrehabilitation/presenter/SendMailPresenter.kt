package com.example.medicalrehabilitation.presenter

import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.medicalrehabilitation.view.SendMailActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SendMailPresenter : SendMailInterface {

    private lateinit var sendMailActivity: SendMailActivity
    private var currentDate: Date = Calendar.getInstance().time
    private var dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    override fun attachView(sendMail: SendMailActivity) {
        this.sendMailActivity = sendMail
    }

    override fun sendEmail(
        sendEmail: Button,
        doctorEmail: EditText,
        trainingSpinner: Spinner,
        painfulSpinner: Spinner,
        failedExercises: EditText,
        theme: String
    ) {
        val dateString: String = dateFormat.format(currentDate)
        sendEmail.setOnClickListener {
            var failedExercisesString: String = failedExercises.text.toString()
            if (failedExercises.text.isEmpty()) {
                failedExercisesString = "Отсутствуют"
            }
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("mailto:${doctorEmail.text}")
            ) //Кому отправляем
                .putExtra(Intent.EXTRA_SUBJECT, theme) //Тема письма
            if (Locale.getDefault().language == "ru") {
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Здравствуйте, сегодня $dateString я провел занятие по медицинской реабилитации после травмы передней крестообразной связки.\n" +
                            "Тренировка прошла: " + trainingSpinner.selectedItem.toString() + ".\n" +
                            "Ощущения боли: " + painfulSpinner.selectedItem.toString() + ".\n" +
                            "Невыполненные упражнения: $failedExercisesString."
                ) //Текст письма
            } else if (Locale.getDefault().language == "en") {
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hello, today $dateString I conducted a medical rehabilitation class after an anterior cruciate ligament injury.\n" +
                            "The training was:" + trainingSpinner.selectedItem.toString() + ".\n" +
                            "Feelings of pain: " + painfulSpinner.selectedItem.toString() + ".\n" +
                            "Unfulfilled exercises: $failedExercisesString."
                ) //Текст письма
            }
            if (doctorEmail.text.isEmpty()) {
                sendMailActivity.enterADoctorsEmail()
            } else sendMailActivity.chooseEmail(intent)
        }
    }
}