package com.example.medicalrehabilitation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.medicalrehabilitation.exercisehistory.ExerciseHistoryDatabaseModel
import com.example.medicalrehabilitation.exercisehistory.ExerciseHistoryModel
import com.example.medicalrehabilitation.exercisehistory.ExerciseHistoryRepositoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BlankFragmentExerciseHistoryListViewModel(application: Application) :
    AndroidViewModel(application) {
}