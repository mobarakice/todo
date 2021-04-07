package com.mobarak.todo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobarak.todo.data.db.dao.TaskDao
import com.mobarak.todo.data.db.entity.Task

/**
 * The Room database that contains the task table
 * @author mobarak
 */
@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, "todo.db")
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}