package com.example.medicalrehabilitation.presenter

import android.media.MediaPlayer
import android.widget.TextView

interface RestInterface {
    fun timerStart(millisInFuture: Long, timerTextView: TextView, soundOfStop: MediaPlayer)
    fun timerPause()
    fun timerResume(timerTextView: TextView, soundOfStop: MediaPlayer)
    fun plus30Sec(millisPlus: Int, timerTextView: TextView, soundOfStop: MediaPlayer)
    fun soundPlay(sound: MediaPlayer)
    fun soundPause(sound: MediaPlayer)
}