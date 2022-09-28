package com.example.medicalrehabilitation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.beginButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_blankFragmentHome_to_blankFragmentTraining)
        } //Кнопка с изображением "Начало тренировки"
        binding.aboutButton.setOnClickListener { viewModel.aboutButtonClicked() } //Кнопка "О приложении"
    }
}