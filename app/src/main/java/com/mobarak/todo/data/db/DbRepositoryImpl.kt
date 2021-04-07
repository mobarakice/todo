package com.mobarak.todo.data.db

import com.mobarak.todo.data.db.entity.Task
import com.mobarak.todo.ui.base.AppApplication
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * This Database repository implementation class, all business logic that related to database will be implemented here
 *
 * @author mobarak
 */
class DbRepositoryImpl private constructor() : DbRepository {
    private val db: AppDatabase?
    override fun observeTasks(): Flowable<List<Task?>?>? {
        return db!!.taskDao().observeTasks()
    }

    override fun observeTaskById(taskId: Long): Flowable<Task?>? {
        return db!!.taskDao().observeTaskById(taskId)
    }

    override val tasks: Single<List<Task?>?>?
        get() = db!!.taskDao().tasks

    override fun getTaskById(taskId: Long): Single<Task?>? {
        return db!!.taskDao().getTaskById(taskId)
    }

    override fun insertTask(task: Task?): Completable? {
        return db!!.taskDao().insertTask(task)
    }

    override fun updateTask(task: Task?): Completable? {
        return db!!.taskDao().updateTask(task)
    }

    override fun updateCompleted(taskId: Long, completed: Boolean): Completable? {
        return db!!.taskDao().updateCompleted(taskId, completed)
    }

    override fun deleteTaskById(taskId: Long): Completable? {
        return db!!.taskDao().deleteTaskById(taskId)
    }

    override fun deleteTasks(): Completable? {
        return db!!.taskDao().deleteTasks()
    }

    override fun deleteCompletedTasks(): Completable? {
        return db!!.taskDao().deleteCompletedTasks()
    }

    companion object {
        private var mInstance: DbRepositoryImpl? = null
        val instance: DbRepositoryImpl?
            get() {
                if (mInstance == null) {
                    synchronized(DbRepositoryImpl::class.java) {
                        if (mInstance == null) {
                            mInstance = DbRepositoryImpl()
                        }
                    }
                }
                return mInstance
            }
    }

    init {
        db = AppDatabase.Companion.getInstance(AppApplication.Companion.getAppContext())
    }
}