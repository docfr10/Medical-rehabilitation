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

    private lateinit var sendMail: SendMailActivity
    private var currentDate: Date = Calendar.getInstance().time
    private var dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    override fun attachView(sendMail: SendMailActivity) {
        this.sendMail = sendMail
    }

    override fun sendEmail(
        sendemail: Button,
        doctoremail: EditText,
        trainingspinner: Spinner,
        painfulspinner: Spinner,
        failedexerciese: EditText,
        theme: String
    ) {
        val dateString: String = dateFormat.format(currentDate)
        sendemail.setOnClickListener {
            var failedexerciesestring: String = failedexerciese.text.toString()
            if (failedexerciese.text.isEmpty()) {
                failedexerciesestring = "Все упражнения были выполнены"
            }
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("mailto:${doctoremail.text}")
            ) //Кому отправляем
            intent.putExtra(Intent.EXTRA_SUBJECT, theme) //Тема письма
            if (Locale.getDefault().language == "ru") {
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Здравствуйте, сегодня $dateString я провел занятие по медицинской реабилитации после травмы передней крестообразной связки.\n" +
                            "Тренировка прошла: " + trainingspinner.selectedItem.toString() + ".\n" +
                            "Ощущения боли: " + painfulspinner.selectedItem.toString() + ".\n" +
                            "Невыполненные упражнения: $failedexerciesestring."
                ) //Текст письма
            } else if (Locale.getDefault().language == "en") {
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hello, today $dateString I conducted a medical rehabilitation class after an anterior cruciate ligament injury.\n" +
                            "The training was:" + trainingspinner.selectedItem.toString() + ".\n" +
                            "Feelings of pain: " + painfulspinner.selectedItem.toString() + ".\n" +
                            "Unfulfilled exercises: $failedexerciesestring."
                ) //Текст письма
            }
            sendMail.chooseEmail(intent)
        }
    }
}