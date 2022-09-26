package com.example.medicalrehabilitation.view

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalrehabilitation.MainActivity
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.ActivitySendMailBinding
import com.example.medicalrehabilitation.presenter.SendMailPresenter


//Класс, отвечающий за отправку сообщения на почту врачу
class SendMailActivity : AppCompatActivity() {
    private var sendMailPresenter: SendMailPresenter = SendMailPresenter()
    private lateinit var binding: ActivitySendMailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySendMailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sendMailPresenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        sendEmail(
            binding.sendButton,
            binding.doctorsmailEditTextTextEmailAddress,
            binding.trainingSpinner,
            binding.painfulSpinner,
            binding.failedEditTextTextMultiLine
        )
    }

    override fun onRestart() {
        super.onRestart()
        transitionToMainActivity()
    }

    fun chooseEmail(intent: Intent) {
        startActivity(Intent.createChooser(intent, getText(R.string.send_mail)))
    }

    private fun sendEmail(
        sendEmail: Button,
        doctorEmail: EditText,
        trainingSpinner: Spinner,
        painfulSpinner: Spinner,
        failedExercises: EditText
    ) {
        sendMailPresenter.sendEmail(
            sendEmail,
            doctorEmail,
            trainingSpinner,
            painfulSpinner,
            failedExercises,
            getText(R.string.app_name) as String
        )
    }

    private fun transitionToMainActivity() {
        val intent = Intent(this@SendMailActivity, MainActivity::class.java)
        startActivity(intent)
    }

    fun enterADoctorsEmail() {
        val toast = Toast.makeText(applicationContext, R.string.fill_email, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.show()
    }
}