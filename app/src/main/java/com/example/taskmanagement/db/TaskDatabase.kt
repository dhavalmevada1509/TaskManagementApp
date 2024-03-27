package com.example.taskmanagement.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanagement.model.TaskModel

@Database(entities = [TaskModel::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getContactDao() : TaskDao

    companion object {
       @Volatile
        private var INSTANCE: TaskDatabase? = null

        private const val DB_NAME = "contact_data.db"

        fun getDatabase(context: Context): TaskDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}