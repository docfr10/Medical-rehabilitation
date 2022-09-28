package com.example.medicalrehabilitation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.databinding.ActivityMainBinding

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

//Класс, отвечающий за работу главного экрана
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
