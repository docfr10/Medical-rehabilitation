package com.example.medicalrehabilitation

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.view.TrainingActivity

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

//Класс, отвечающий за работу главного экрана
class MainActivity : AppCompatActivity() {
    private lateinit var beginButton: Button //Кнопка с изображением "Начало тренировки"
    private lateinit var aboutButton: Button //Кнопка "О приложении"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Присваиваем значения в коде к значениям в разметке
        beginButton = findViewById(R.id.begin_button)
        aboutButton = findViewById(R.id.about_button)
    }

    override fun onResume() {
        super.onResume()
        buttonClick()
    }

    //Метод, с помошью которого можно взаимодействовать с кнопками
    private fun buttonClick() {
        //Переход с помощью кнопки к тренировке
        beginButton.setOnClickListener {
            val intent = Intent(this@MainActivity, TrainingActivity::class.java)
            startActivity(intent)
        }

        //Переход с помощью кнопки к информации о приложении
        aboutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.about_app)
                .setTitle(getString(R.string.about))
                .setPositiveButton(getString(R.string.clear)) { dialog, _ -> dialog.cancel() }
                .create()
                .show()
        }
    }
}
