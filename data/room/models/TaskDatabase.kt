package com.example.compose_demo.data.room.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Task::class,Day::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao():TaskDao
    abstract fun dayDao():DayDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}