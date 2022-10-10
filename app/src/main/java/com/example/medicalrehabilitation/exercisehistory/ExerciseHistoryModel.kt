package com.example.medicalrehabilitation.exercisehistory

import androidx.room.Entity
import androidx.room.PrimaryKey

//Объект, представляющий собой данные о тренировке
@Entity(tableName = "exercise_history_table")
data class ExerciseHistoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dateOfExercise: String,
    val howWasExercise: String,
    val howWasPainful: String,
    val failedExercises: String
)