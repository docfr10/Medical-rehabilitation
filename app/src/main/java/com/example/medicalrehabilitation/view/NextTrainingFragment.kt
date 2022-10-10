package com.example.medicalrehabilitation.view

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.medicalrehabilitation.viewmodel.NextTrainingViewModelFragment
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentNextTrainingBinding
import com.example.medicalrehabilitation.model.notifications.NotificationsModel
import java.util.*

//Класс, отвечающий за работу экрана "Следующая тренировка"
class NextTrainingFragment : Fragment() {
    private lateinit var viewModel: NextTrainingViewModelFragment
    private lateinit var binding: FragmentNextTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNextTrainingBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[NextTrainingViewModelFragment::class.java]

        createNotificationChannel()
        //С помощью binding обрабатываем нажатия на кнопку
        binding.button.setOnClickListener { createNotifications() }
        binding.timePicker.setIs24HourView(true)

        //Обработка Back Stack
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.getTouchCounter().value == 1)
                    findNavController().navigate(R.id.action_blankFragmentNextTraining_to_blankFragmentHome)
                else {
                    val toast =
                        Toast.makeText(context, R.string.return_to_main_menu, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.BOTTOM, 0, 0)
                    toast.show()
                    viewModel.timerForTouch()
                    viewModel.changeTouchCounter(1)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
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
        viewModel.getShowAlert().observe(viewLifecycleOwner) {
            //Проверка на то просмотрел ли пользователь уведомление
            if (it == true)
            //Если да, то запускаем FragmentSendMail
                sendMail()
        }
    }

    private fun sendMail() {
        findNavController().navigate(R.id.action_blankFragmentNextTraining_to_blankFragmentSendMail)
    }
}