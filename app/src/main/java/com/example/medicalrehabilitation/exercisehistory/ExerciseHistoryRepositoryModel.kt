package com.example.medicalrehabilitation.exercisehistory

import androidx.lifecycle.LiveData

class ExerciseHistoryRepositoryModel(private val exerciseHistoryDaoModel: ExerciseHistoryDaoModel) {
    val readAllData: LiveData<List<ExerciseHistoryModel>> = exerciseHistoryDaoModel.readAllData()

    fun addDate(exerciseHistoryModel: ExerciseHistoryModel) {
        exerciseHistoryDaoModel.addDate(exerciseHistoryModel)
    }

    //fun getExercises() = exerciseHistoryDaoModel.readAllData()
}