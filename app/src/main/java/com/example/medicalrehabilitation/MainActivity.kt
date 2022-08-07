package com.example.medicalrehabilitation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var beginimagebutton: ImageButton //Кнопка с изображением "Начало тренировки"
    private lateinit var aboutbutton: Button //Кнопка "О приложении"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Присваиваем значения в коде к значениям в разметке
        beginimagebutton = findViewById(R.id.begin_imagebutton)
        aboutbutton = findViewById(R.id.about_button)
    }

    override fun onResume() {
        super.onResume()
        buttonClick()
    }

    //Метод, с помошью которого можно взаимодействовать с кнопками
    private fun buttonClick() {
        //Переход с помощью кнопки к тренировке
        beginimagebutton.setOnClickListener {
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