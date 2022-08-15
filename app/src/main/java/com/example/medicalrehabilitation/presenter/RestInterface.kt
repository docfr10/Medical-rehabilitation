package com.example.medicalrehabilitation.presenter

import android.media.MediaPlayer
import android.widget.TextView

interface RestInterface {
    fun timerStart(millisInFuture: Long, timertextView: TextView, soundOfStop: MediaPlayer)
    fun timerPause()
    fun timerResume(timertextView: TextView, soundOfStop: MediaPlayer)
    fun plus30Sec(millisPlus: Int, timertextView: TextView, soundOfStop: MediaPlayer)
    fun soundPlay(sound: MediaPlayer)
    fun soundPause(sound: MediaPlayer)
}