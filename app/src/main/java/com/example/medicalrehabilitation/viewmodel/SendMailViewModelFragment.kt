package com.example.medicalrehabilitation.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.CountDownTimer
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentSendMailBinding
import com.example.medicalrehabilitation.exercisehistory.ExerciseHistoryModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SendMailViewModelFragment(application: Application) : AndroidViewModel(application) {
    private var timer: CountDownTimer? = null //Таймер

    private val currentDate: Date = Calendar.getInstance().time
    private val dateFormat: DateFormat =
        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    private val dateString: String = dateFormat.format(currentDate)

    private val mutableLiveDataIntent = MutableLiveData<Intent>()
    private val touchCounter = MutableLiveData<Int>().apply { postValue(0) }

    fun getMutableLiveDataIntent(): MutableLiveData<Intent> {
        return mutableLiveDataIntent
    }

    fun getTouchCounter(): MutableLiveData<Int> {
        return touchCounter
    }

    fun changeTouchCounter(state: Int) {
        touchCounter.value = state
    }

    fun timerForTouch() {
        timer = object : CountDownTimer(2000, 1) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                changeTouchCounter(0)
            }
        }
        (timer as CountDownTimer).start()
    }

    fun sendEmail(
        binding: FragmentSendMailBinding,
        theme: String
    ) {
        var failedExercisesString: String = binding.failedEditTextTextMultiLine.text.toString()
        if (binding.failedEditTextTextMultiLine.text.isEmpty()) {
            failedExercisesString = "Отсутствуют"
        }
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("mailto:${binding.doctorsmailEditTextTextEmailAddress.text}")
        ) //Кому отправляем
            .putExtra(Intent.EXTRA_SUBJECT, theme) //Тема письма
        if (Locale.getDefault().language == "ru") {
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Здравствуйте, сегодня $dateString я провел занятие по медицинской реабилитации после травмы передней крестообразной связки.\n" +
                        "Тренировка прошла: " + binding.trainingSpinner.selectedItem.toString() + ".\n" +
                        "Ощущения боли: " + binding.painfulSpinner.selectedItem.toString() + ".\n" +
                        "Невыполненные упражнения: $failedExercisesString."
            ) //Текст письма
        } else if (Locale.getDefault().language == "en") {
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Hello, today $dateString I conducted a medical rehabilitation class after an anterior cruciate ligament injury.\n" +
                        "The training was:" + binding.trainingSpinner.selectedItem.toString() + ".\n" +
                        "Feelings of pain: " + binding.painfulSpinner.selectedItem.toString() + ".\n" +
                        "Unfulfilled exercises: $failedExercisesString."
            ) //Текст письма
        }
        if (binding.doctorsmailEditTextTextEmailAddress.text.isEmpty()) {
            enterADoctorsEmail()
        } else chooseEmail(intent)

    }

    private fun chooseEmail(intent: Intent) {
        mutableLiveDataIntent.value = intent
    }

    private fun enterADoctorsEmail() {
        val toast = Toast.makeText(getApplication(), R.string.fill_email, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.show()
    }

    //Записываем каждое значение элемента из Fragment и передаем его в БД
    fun insertDataToDatabase(binding: FragmentSendMailBinding): ExerciseHistoryModel {
        val howWasPainful = binding.painfulSpinner.selectedItem.toString()
        val howWasExercise = binding.trainingSpinner.selectedItem.toString()
        val failedExercises = binding.failedEditTextTextMultiLine.text.toString()

        return ExerciseHistoryModel(
            0, dateString,
            howWasExercise,
            howWasPainful,
            failedExercises
        )
    }
}