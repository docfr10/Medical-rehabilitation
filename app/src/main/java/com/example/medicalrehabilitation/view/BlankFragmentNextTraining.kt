package com.example.medicalrehabilitation.view

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.medicalrehabilitation.viewmodel.BlankFragmentNextTrainingViewModel
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentNextTrainingBinding
import com.example.medicalrehabilitation.model.notifications.NotificationsModel
import java.util.*

class BlankFragmentNextTraining : Fragment() {
    private lateinit var viewModel: BlankFragmentNextTrainingViewModel
    private lateinit var binding: FragmentBlankFragmentNextTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankFragmentNextTrainingBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[BlankFragmentNextTrainingViewModel::class.java]

        createNotificationChannel()
        //С помощью binding обрабатываем нажатия на кнопку
        binding.button.setOnClickListener { createNotifications() }
        binding.timePicker.setIs24HourView(true)

        return binding.root
    }

    private fun createNotificationChannel() {
        val notificationManager =
            activity?.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        viewModel.createNotificationChannel(notificationManager)
    }

    private fun createNotifications() {
        val title: String = resources.getString(R.string.notification) //Название уведомления
        val message: String = resources.getString(R.string.training_time) //Текст уведомления
        val intent = Intent(context, NotificationsModel::class.java)
        //Записываем дату когда необходимо отправить уведомление
        val alarmManager =
            activity?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val time = viewModel.getTime(binding)
        viewModel.createNotifications(
            intent,
            alarmManager,
            time,
            title,
            message
        )
        showAlert()
    }

    private fun showAlert() {
        val time = viewModel.getTime(binding)
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(context)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(context)
        val builder = AlertDialog.Builder(context)
        viewModel.showAlert(date, dateFormat, timeFormat, builder)
        viewModel.showAlert.observe(viewLifecycleOwner) {
            if (it == true)
                sendMail()
        }
    }

    private fun sendMail() {
        findNavController().navigate(R.id.action_blankFragmentNextTraining_to_blankFragmentSendMail)
    }
}