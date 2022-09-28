//package com.example.medicalrehabilitation.trash
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import com.example.medicalrehabilitation.MainActivity
//import com.example.medicalrehabilitation.R
//import com.example.medicalrehabilitation.databinding.ActivitySendMailBinding
//import com.example.medicalrehabilitation.trash.SendMailViewModel
//
//
////Класс, отвечающий за отправку сообщения на почту врачу
//class SendMailActivity : AppCompatActivity() {
//    private lateinit var binding: ActivitySendMailBinding
//    private lateinit var viewModel: SendMailViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivitySendMailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val provider = ViewModelProvider(this)
//        viewModel = provider.get(SendMailViewModel::class.java)
//        sendEmail(binding)
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        transitionToMainActivity()
//    }
//
//    private fun chooseEmail() {
//        viewModel.mutableLiveDataIntent.observe(this) {
//            startActivity(Intent.createChooser(it, getText(R.string.send_mail)))
//        }
//    }
//
//    private fun sendEmail(binding: ActivitySendMailBinding) {
//        viewModel.sendEmail(binding, getText(R.string.app_name) as String)
//        chooseEmail()
//    }
//
//    private fun transitionToMainActivity() {
//        val intent = Intent(this@SendMailActivity, MainActivity::class.java)
//        startActivity(intent)
//    }
//}