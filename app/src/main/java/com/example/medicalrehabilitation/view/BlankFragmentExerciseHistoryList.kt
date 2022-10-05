package com.example.medicalrehabilitation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentExerciseHistoryListBinding
import com.example.medicalrehabilitation.exercisehistory.ExerciseHistoryAdapter
import com.example.medicalrehabilitation.viewmodel.BlankFragmentExerciseHistoryViewModel

class BlankFragmentExerciseHistoryList : Fragment() {
    private lateinit var viewModel: BlankFragmentExerciseHistoryViewModel
    private lateinit var binding: FragmentBlankFragmentExerciseHistoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentBlankFragmentExerciseHistoryListBinding.inflate(inflater, container, false)

        val adapter = ExerciseHistoryAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val provider = ViewModelProvider(this)
        viewModel = provider[BlankFragmentExerciseHistoryViewModel::class.java]
        //Обновление отображения данных на экране из БД
        viewModel.readAllData.observe(viewLifecycleOwner) { exercise ->
            adapter.setData(exercise)
        }

        //Обработка Back Stack
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_blankFragmentExerciseHistoryList_to_blankFragmentHome)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return binding.root
    }
}