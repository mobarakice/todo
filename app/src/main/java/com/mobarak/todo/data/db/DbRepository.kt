package com.mobarak.todo.data.db

import com.mobarak.todo.data.db.entity.Task
import kotlinx.coroutines.flow.Flow

/**
 * This is repository class for database and handle all kind of database operation through this
 * @author mobarak
 */
interface DbRepository {
    fun observeTasks(): Flow<List<Task>>
    fun observeTaskById(taskId: Long): Flow<Task>
    suspend fun getTasks(): List<Task>
    suspend fun getTaskById(taskId: Long): Task?
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task): Int
    suspend fun updateCompleted(taskId: Long, completed: Boolean): Int
    suspend fun deleteTaskById(taskId: Long): Int
    suspend fun deleteTasks()
    suspend fun deleteCompletedTasks(): Int
}