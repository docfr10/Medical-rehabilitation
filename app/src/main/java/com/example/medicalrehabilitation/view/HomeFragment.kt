package com.example.medicalrehabilitation.view

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.medicalrehabilitation.viewmodel.HomeViewModelFragment
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

//Класс, отвечающий за работу главного экрана
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModelFragment
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[HomeViewModelFragment::class.java]

        //Обработка Back Stack
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.getTouchCounter().value == 1)
                    activity?.finish()
                else {
                    val toast =
                        Toast.makeText(context, R.string.exit_app, Toast.LENGTH_SHORT)
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

    override fun onResume() {
        super.onResume()
        binding.beginButton.setOnClickListener {
            //Переход на TrainingFragment
            it.findNavController().navigate(R.id.action_blankFragmentHome_to_blankFragmentTraining)
        } //Кнопка с изображением "Начало тренировки"

        binding.exerciseHistory.setOnClickListener {
            //Переход на ExerciseHistoryListFragment
            it.findNavController()
                .navigate(R.id.action_blankFragmentHome_to_blankFragmentExerciseHistoryList)
        }

        binding.aboutButton.setOnClickListener { aboutButtonClicked() } //Кнопка "О приложении"
    }

    private fun aboutButtonClicked() {
        //Создание диалогового окна с информацией о приложении
        val builder = context?.let {
            MaterialAlertDialogBuilder(it)
                .setMessage(R.string.about_app)
                .setTitle((R.string.about))
                .setPositiveButton((R.string.clear)) { dialog, _ -> dialog.cancel() }
        }

        //Анимация окна
        val dialog = builder?.create()
        dialog?.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
        dialog?.show()
    }
}