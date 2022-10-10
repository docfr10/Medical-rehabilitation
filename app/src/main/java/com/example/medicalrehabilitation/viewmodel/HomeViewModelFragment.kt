package com.example.medicalrehabilitation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class HomeViewModelFragment(application: Application) : AndroidViewModel(application) {
    private val touchCounter = MutableLiveData<Int>().apply { postValue(0) }
    private var timer: CountDownTimer? = null //Таймер

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
}