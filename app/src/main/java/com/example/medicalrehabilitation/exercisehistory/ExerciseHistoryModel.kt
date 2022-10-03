package com.example.medicalrehabilitation.exercisehistory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_history_table")
data class ExerciseHistoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dateOfExercise: String,
    val howWasExercise: String,
    val howWasPainful: String,
    val failedExercises: String
)