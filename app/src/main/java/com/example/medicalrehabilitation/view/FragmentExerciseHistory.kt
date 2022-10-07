package com.example.medicalrehabilitation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.medicalrehabilitation.databinding.FragmentExerciseHistoryBinding

class FragmentExerciseHistory : Fragment() {

    private lateinit var binding: FragmentExerciseHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseHistoryBinding.inflate(inflater, container, false)

        return binding.root
    }
}