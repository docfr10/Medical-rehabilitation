package com.example.medicalrehabilitation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class BlankFragmentHomeViewModel(application: Application) : AndroidViewModel(application) {
    var touchCounter = MutableLiveData<Int>().apply { postValue(0) }
    private var timer: CountDownTimer? = null //Таймер

    fun timerForTouch() {
        timer = object : CountDownTimer(2000, 1) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                touchCounter.value = 0
            }
        }
        (timer as CountDownTimer).start()
    }
}