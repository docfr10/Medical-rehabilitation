//package com.example.medicalrehabilitation.trash
//
//import android.app.*
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import com.example.medicalrehabilitation.R
//import com.example.medicalrehabilitation.model.NotificationsModel
//import com.example.medicalrehabilitation.databinding.ActivityNextTrainingBinding
//import com.example.medicalrehabilitation.trash.NextTrainingViewModel
//import java.util.*
//
////Класс, отвечающий за выбор даты следующей тренировки
//class NextTrainingActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityNextTrainingBinding //Библиотека binding упрощает работу с компонентами GUI
//    private lateinit var viewModel: NextTrainingViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityNextTrainingBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val provider = ViewModelProvider(this)
//        viewModel = provider.get(NextTrainingViewModel::class.java)
//
//        createNotificationChannel()
//        //С помощью binding обрабатываем нажатия на кнопку
//        binding.button.setOnClickListener { createNotifications() }
//        binding.timePicker.setIs24HourView(true)
//    }
//
//    private fun createNotificationChannel() {
//        val notificationManager =
//            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        viewModel.createNotificationChannel(notificationManager)
//    }
//
//    private fun createNotifications() {
//        val title: String = resources.getString(R.string.notification) //Название уведомления
//        val message: String = resources.getString(R.string.training_time) //Текст уведомления
//        val intent = Intent(applicationContext, NotificationsModel::class.java)
//        //Записываем дату когда необходимо отправить уведомление
//        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val time = viewModel.getTime(binding)
//        viewModel.createNotifications(
//            intent,
//            applicationContext,
//            alarmManager,
//            time,
//            title,
//            message
//        )
//        showAlert()
//    }
//
//    private fun showAlert() {
//        val time = viewModel.getTime(binding)
//        val date = Date(time)
//        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
//        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)
//        val builder = AlertDialog.Builder(this)
//        viewModel.showAlert(date, dateFormat, timeFormat, builder)
//        viewModel.showAlert.observe(this) {
//            if (it == true)
//                sendMail()
//        }
//    }
//
//    private fun sendMail() {
//        val intent1 = Intent(this@NextTrainingActivity, SendMailActivity::class.java)
//        startActivity(intent1)
//    }
//}