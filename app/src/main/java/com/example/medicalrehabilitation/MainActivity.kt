package com.example.medicalrehabilitation

import android.app.*
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.databinding.ActivityMainBinding
import com.example.medicalrehabilitation.view.TrainingActivity

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

    override fun onResume() {
        super.onResume()
        binding.beginButton.setOnClickListener { beginButtonClicked() } //Кнопка с изображением "Начало тренировки"
        binding.aboutButton.setOnClickListener { aboutButtonClicked() } //Кнопка "О приложении"
    }

    private fun beginButtonClicked() {
        val intent = Intent(this@MainActivity, TrainingActivity::class.java)
        startActivity(intent)
    }

    private fun aboutButtonClicked() {
        AlertDialog.Builder(this)
            .setMessage(R.string.about_app)
            .setTitle(getString(R.string.about))
            .setPositiveButton(getString(R.string.clear)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }
}
