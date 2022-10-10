package com.example.medicalrehabilitation.exercisehistory

import androidx.lifecycle.LiveData

class ExerciseHistoryRepositoryModel(private val exerciseHistoryDaoModel: ExerciseHistoryDaoModel) {

    fun addDate(exerciseHistoryModel: ExerciseHistoryModel) {
        exerciseHistoryDaoModel.addDate(exerciseHistoryModel)
    }

    fun getExercises(): LiveData<List<ExerciseHistoryModel>> {
        return exerciseHistoryDaoModel.readAllData()
    }
}