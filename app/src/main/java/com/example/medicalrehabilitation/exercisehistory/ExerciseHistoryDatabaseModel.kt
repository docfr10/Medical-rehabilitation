package com.example.medicalrehabilitation.exercisehistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExerciseHistoryModel::class], version = 1, exportSchema = false)
abstract class ExerciseHistoryDatabaseModel : RoomDatabase() {
    abstract fun exerciseHistoryDaoModel(): ExerciseHistoryDaoModel

    companion object {
        @Volatile
        private var INSTANCE: ExerciseHistoryDatabaseModel? = null

        fun getDatabase(context: Context): ExerciseHistoryDatabaseModel {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseHistoryDatabaseModel::class.java,
                    "exercise_history_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}