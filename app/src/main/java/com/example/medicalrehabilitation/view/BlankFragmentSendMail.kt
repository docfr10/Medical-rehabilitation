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
import com.example.medicalrehabilitation.viewmodel.BlankFragmentSendMailViewModel
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentExerciseHistoryBinding
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentSendMailBinding
import com.example.medicalrehabilitation.viewmodel.BlankFragmentExerciseHistoryViewModel

class BlankFragmentSendMail : Fragment() {
    private lateinit var viewModel: BlankFragmentSendMailViewModel
    private lateinit var viewModelBlankFragmentExerciseHistoryListViewModel: BlankFragmentExerciseHistoryViewModel

    private lateinit var binding: FragmentBlankFragmentSendMailBinding
    private lateinit var bindingExerciseHistory: FragmentBlankFragmentExerciseHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankFragmentSendMailBinding.inflate(inflater, container, false)
        bindingExerciseHistory =
            FragmentBlankFragmentExerciseHistoryBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[BlankFragmentSendMailViewModel::class.java]

        val providerExerciseHistory = ViewModelProvider(this)
        viewModelBlankFragmentExerciseHistoryListViewModel =
            providerExerciseHistory[BlankFragmentExerciseHistoryViewModel::class.java]

        binding.sendButton.setOnClickListener {
            sendEmail(binding)
        }
        binding.onMenuButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_blankFragmentSendMail_to_blankFragmentHome)
        }

        //После того как открылся выбор почты происходит запись данных в БД
        viewModel.mutableLiveDataIntent.observe(viewLifecycleOwner) {
            insertDataToDatabase(binding)
        }

        //Обработка Back Stack
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.touchCounter.value == 1)
                    findNavController().navigate(R.id.action_blankFragmentSendMail_to_blankFragmentHome)
                else {
                    val toast =
                        Toast.makeText(context, R.string.return_to_main_menu, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.BOTTOM, 0, 0)
                    toast.show()
                    viewModel.timerForTouch()
                    viewModel.touchCounter.value = 1
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return binding.root
    }

    private fun chooseEmail() {
        viewModel.mutableLiveDataIntent.observe(viewLifecycleOwner) {
            startActivity(Intent.createChooser(it, getText(R.string.send_mail)))
        }
    }

    private fun sendEmail(binding: FragmentBlankFragmentSendMailBinding) {
        viewModel.sendEmail(binding, getText(R.string.app_name) as String)
        chooseEmail()
    }

    private fun insertDataToDatabase(binding: FragmentBlankFragmentSendMailBinding) {
        viewModelBlankFragmentExerciseHistoryListViewModel.addDate(
            viewModel.insertDataToDatabase(
                binding
            )
        )
    }
}