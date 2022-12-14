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
import com.example.medicalrehabilitation.databinding.FragmentExerciseHistoryListBinding
import com.example.medicalrehabilitation.model.exercisehistory.ExerciseHistoryAdapter
import com.example.medicalrehabilitation.viewmodel.ExerciseHistoryViewModelFragment

//Класс, отвечающий за работу экрана "История тренировок"
class ExerciseHistoryListFragment : Fragment() {
    private lateinit var viewModel: ExerciseHistoryViewModelFragment
    private lateinit var binding: FragmentExerciseHistoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentExerciseHistoryListBinding.inflate(inflater, container, false)

        val adapter = ExerciseHistoryAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val provider = ViewModelProvider(this)
        viewModel = provider[ExerciseHistoryViewModelFragment::class.java]
        //Обновление отображения данных на экране из БД
        viewModel.getReadAllData().observe(viewLifecycleOwner) { exercise ->
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