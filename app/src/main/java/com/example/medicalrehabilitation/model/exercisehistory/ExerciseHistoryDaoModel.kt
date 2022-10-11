package com.example.medicalrehabilitation.model.exercisehistory

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//Интерфейс, описывающий методы работы с БД
@Dao
interface ExerciseHistoryDaoModel {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addDate(exerciseHistoryModel: ExerciseHistoryModel) //Добавляем новую дату тренировки

    @Query("SELECT * FROM exercise_history_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<ExerciseHistoryModel>> //Вывести все даты тренировок
}