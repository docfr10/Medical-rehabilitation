package com.example.medicalrehabilitation.exercisehistory

import androidx.lifecycle.LiveData
import com.example.medicalrehabilitation.exercisehistory.ExerciseHistoryDaoModel
import com.example.medicalrehabilitation.exercisehistory.ExerciseHistoryModel

class ExerciseHistoryRepositoryModel(private val exerciseHistoryDaoModel: ExerciseHistoryDaoModel) {
    val readAllData: LiveData<List<ExerciseHistoryModel>> = exerciseHistoryDaoModel.readAllData()

    fun addDate(exerciseHistoryModel: ExerciseHistoryModel) {
        exerciseHistoryDaoModel.addDate(exerciseHistoryModel)
    }
}