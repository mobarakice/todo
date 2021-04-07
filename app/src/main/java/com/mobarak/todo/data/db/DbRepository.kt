package com.mobarak.todo.data.db

import com.mobarak.todo.data.db.entity.Task
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * This is repository class for database and handle all kind of database operation through this
 * @author mobarak
 */
interface DbRepository {
    fun observeTasks(): Flowable<List<Task?>?>?
    fun observeTaskById(taskId: Long): Flowable<Task?>?
    val tasks: Single<List<Task?>?>?
    fun getTaskById(taskId: Long): Single<Task?>?
    fun insertTask(task: Task?): Completable?
    fun updateTask(task: Task?): Completable?
    fun updateCompleted(taskId: Long, completed: Boolean): Completable?
    fun deleteTaskById(taskId: Long): Completable?
    fun deleteTasks(): Completable?
    fun deleteCompletedTasks(): Completable?
}