package com.example.medicalrehabilitation.model.exercisehistory

import androidx.lifecycle.LiveData

//Класс, осуществляющий связь БД и кода
class ExerciseHistoryRepositoryModel(private val exerciseHistoryDaoModel: ExerciseHistoryDaoModel) {

    fun addDate(exerciseHistoryModel: ExerciseHistoryModel) {
        exerciseHistoryDaoModel.addDate(exerciseHistoryModel)
    }

    fun getExercises(): LiveData<List<ExerciseHistoryModel>> {
        return exerciseHistoryDaoModel.readAllData()
    }
}