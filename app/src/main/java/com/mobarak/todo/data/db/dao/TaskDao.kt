package com.mobarak.todo.data.db.dao

import androidx.room.*
import com.mobarak.todo.data.db.entity.Task
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the task table.
 * @author mobarak
 */
@Dao
interface TaskDao {
    /**
     * Observes list of tasks.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Tasks")
    fun observeTasks(): Flow<List<Task>>

    /**
     * Observes a single task.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM Tasks WHERE id = :taskId")
    fun observeTaskById(taskId: Long): Flow<Task>

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Tasks")
    suspend fun getTasks(): List<Task>

    /**
     * Select a task by id.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM Tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): Task?

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    /**
     * Update a task.
     *
     * @param task task to be updated
     * @return the number of tasks updated. This should always be 1.
     */
    @Update
    suspend fun updateTask(task: Task): Int

    /**
     * Update the complete status of a task
     *
     * @param taskId    id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE tasks SET is_completed = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: Long, completed: Boolean): Int

    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM Tasks WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: Long): Int

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM Tasks")
    suspend fun deleteTasks()

    /**
     * Delete all completed tasks from the table.
     *
     * @return the number of tasks deleted.
     */
    @Query("DELETE FROM Tasks WHERE is_completed = 1")
    suspend fun deleteCompletedTasks(): Int
}