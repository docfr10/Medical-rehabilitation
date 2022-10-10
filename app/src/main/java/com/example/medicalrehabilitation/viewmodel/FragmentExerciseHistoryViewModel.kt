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

class FragmentExerciseHistoryViewModel(application: Application) :
    AndroidViewModel(application) {

    private val exerciseHistoryDaoModel =
        ExerciseHistoryDatabaseModel.getDatabase(application).exerciseHistoryDaoModel()
    private val repository: ExerciseHistoryRepositoryModel =
        ExerciseHistoryRepositoryModel(exerciseHistoryDaoModel)
    private val readAllData: LiveData<List<ExerciseHistoryModel>> = repository.getExercises()

    fun getReadAllData(): LiveData<List<ExerciseHistoryModel>> {
        return readAllData
    }

    fun addDate(exerciseHistoryModel: ExerciseHistoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDate(exerciseHistoryModel)
        }
    }
}