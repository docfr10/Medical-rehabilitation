package com.example.medicalrehabilitation.view

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.medicalrehabilitation.viewmodel.SendMailViewModelFragment
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentExerciseHistoryBinding
import com.example.medicalrehabilitation.databinding.FragmentSendMailBinding
import com.example.medicalrehabilitation.viewmodel.ExerciseHistoryViewModelFragment

//Класс, отвечающий за работу экрана "Отправка письма"
class SendMailFragment : Fragment() {
    private lateinit var viewModel: SendMailViewModelFragment
    private lateinit var viewModelBlankFragmentExerciseHistoryListViewModel: ExerciseHistoryViewModelFragment

    private lateinit var binding: FragmentSendMailBinding
    private lateinit var bindingExerciseHistory: FragmentExerciseHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendMailBinding.inflate(inflater, container, false)
        bindingExerciseHistory =
            FragmentExerciseHistoryBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[SendMailViewModelFragment::class.java]

        val providerExerciseHistory = ViewModelProvider(this)
        viewModelBlankFragmentExerciseHistoryListViewModel =
            providerExerciseHistory[ExerciseHistoryViewModelFragment::class.java]

        binding.sendButton.setOnClickListener {
            sendEmail(binding)
        }
        binding.onMenuButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_blankFragmentSendMail_to_blankFragmentHome)
        }

        //Обработка Back Stack
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.getTouchCounter().value == 1)
                    findNavController().navigate(R.id.action_blankFragmentSendMail_to_blankFragmentHome)
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

    private fun chooseEmail() {
        viewModel.getMutableLiveDataIntent().observe(viewLifecycleOwner) {
            startActivity(Intent.createChooser(it, getText(R.string.send_mail)))
            insertDataToDatabase(binding)
        }
    }

    private fun sendEmail(binding: FragmentSendMailBinding) {
        viewModel.sendEmail(binding, getText(R.string.app_name) as String)
        chooseEmail()
    }

    private fun insertDataToDatabase(binding: FragmentSendMailBinding) {
        viewModelBlankFragmentExerciseHistoryListViewModel.addDate(
            viewModel.insertDataToDatabase(
                binding
            )
        )
    }
}