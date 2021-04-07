package com.mobarak.todo.data.db.dao

import androidx.room.*
import com.mobarak.todo.data.db.entity.Task
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

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
    fun observeTasks(): Flowable<List<Task?>?>?

    /**
     * Observes a single task.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM Tasks WHERE id = :taskId")
    fun observeTaskById(taskId: Long): Flowable<Task?>?

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @get:Query("SELECT * FROM Tasks")
    val tasks: Single<List<Task?>?>?

    /**
     * Select a task by id.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM Tasks WHERE id = :taskId")
    fun getTaskById(taskId: Long): Single<Task?>?

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task?): Completable?

    /**
     * Update a task.
     *
     * @param task task to be updated
     * @return the number of tasks updated. This should always be 1.
     */
    @Update
    fun updateTask(task: Task?): Completable?

    /**
     * Update the complete status of a task
     *
     * @param taskId    id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE tasks SET is_completed = :completed WHERE id = :taskId")
    fun updateCompleted(taskId: Long, completed: Boolean): Completable?

    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM Tasks WHERE id = :taskId")
    fun deleteTaskById(taskId: Long): Completable?

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM Tasks")
    fun deleteTasks(): Completable?

    /**
     * Delete all completed tasks from the table.
     *
     * @return the number of tasks deleted.
     */
    @Query("DELETE FROM Tasks WHERE is_completed = 1")
    fun deleteCompletedTasks(): Completable?
}