package com.example.medicalrehabilitation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startbutton: ImageButton = findViewById(R.id.begin_imagebutton)
        val aboutbutton: Button = findViewById(R.id.about_button)

        //Переход с помощью кнопки к тренировке
        startbutton.setOnClickListener {
            val intent = Intent(this@MainActivity, Training::class.java)
            startActivity(intent)
        }

        //Переход с помощью кнопки к информации о приложении
        aboutbutton.setOnClickListener {
            val intent = Intent(this@MainActivity, About::class.java)
            startActivity(intent)
        }
    }
}