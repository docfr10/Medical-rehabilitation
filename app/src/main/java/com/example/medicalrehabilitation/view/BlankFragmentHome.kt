package com.example.medicalrehabilitation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.medicalrehabilitation.viewmodel.BlankFragmentHomeViewModel
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentHomeBinding

class BlankFragmentHome : Fragment() {

    private lateinit var viewModel: BlankFragmentHomeViewModel
    private lateinit var binding: FragmentBlankFragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankFragmentHomeBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[BlankFragmentHomeViewModel::class.java]

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.touchCounter.value == 1)
                    activity?.finish()
                else {
                    val toast =
                        Toast.makeText(context, R.string.exit_app, Toast.LENGTH_SHORT)
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

    override fun onResume() {
        super.onResume()
        binding.beginButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_blankFragmentHome_to_blankFragmentTraining)
        } //Кнопка с изображением "Начало тренировки"

        binding.exerciseHistory.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_blankFragmentHome_to_blankFragmentExerciseHistoryList)
        }

        binding.aboutButton.setOnClickListener { aboutButtonClicked() } //Кнопка "О приложении"
    }

    private fun aboutButtonClicked() {
        val builder = AlertDialog.Builder(context)
            .setMessage(R.string.about_app)
            .setTitle((R.string.about))
            .setPositiveButton((R.string.clear)) { dialog, _ -> dialog.cancel() }

        val dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
        dialog.show()
    }
}