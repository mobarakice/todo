package com.mobarak.todo.data.db

import android.content.Context
import androidx.room.Room
import com.mobarak.todo.data.db.entity.Task
import com.mobarak.todo.ui.base.AppApplication
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * This Database repository implementation class, all business logic that related to database will be implemented here
 *
 * @author mobarak
 */
class DbRepositoryImpl private constructor() : DbRepository {

    init {
        db = AppDatabase.getInstance(AppApplication.appContext)!!
    }

    companion object {
        private lateinit var db: AppDatabase

        @Volatile
        private var INSTANCE: DbRepositoryImpl? = null
        fun getInstance(): DbRepositoryImpl {
            if (INSTANCE == null) {
                synchronized(DbRepositoryImpl::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DbRepositoryImpl()
                    }
                }
            }
            return INSTANCE!!
        }
    }


    override fun observeTasks(): Flow<List<Task>> {
        return db.taskDao().observeTasks()
    }


    override fun observeTaskById(taskId: Long): Flow<Task> {
        return db.taskDao().observeTaskById(taskId)
    }

    override suspend fun getTasks(): List<Task> {
        return db.taskDao().getTasks()
    }

    override suspend fun getTaskById(taskId: Long): Task? = db.taskDao().getTaskById(taskId)

    override suspend fun insertTask(task: Task) {
        coroutineScope {
            launch { db.taskDao().insertTask(task) }
        }
    }

    override suspend fun updateTask(task: Task): Int {
        return db.taskDao().updateTask(task)
    }

    override suspend fun updateCompleted(taskId: Long, completed: Boolean): Int {
        return db.taskDao().updateCompleted(taskId, completed)
    }

    override suspend fun deleteTaskById(taskId: Long): Int {
        return db.taskDao().deleteTaskById(taskId)
    }

    override suspend fun deleteTasks() {
        db.taskDao().deleteTasks()
    }

    override suspend fun deleteCompletedTasks(): Int {
        return db.taskDao().deleteCompletedTasks()
    }
}