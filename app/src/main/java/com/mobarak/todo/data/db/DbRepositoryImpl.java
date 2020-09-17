package com.mobarak.todo.data.db;

import com.mobarak.todo.AppApplication;
import com.mobarak.todo.data.db.entity.Task;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class DbRepositoryImpl implements DbRepository {

    private static DbRepositoryImpl mInstance;

    private AppDatabase db;

    private DbRepositoryImpl() {
        db = AppDatabase.getInstance(AppApplication.getAppContext());
    }

    public static DbRepositoryImpl getInstance() {
        if (mInstance == null) {
            synchronized (DbRepositoryImpl.class) {
                if (mInstance == null) {
                    mInstance = new DbRepositoryImpl();
                }
            }
        }
        return mInstance;
    }

    @Override
    public Flowable<List<Task>> observeTasks() {
        return db.taskDao().observeTasks();
    }

    @Override
    public Flowable<Task> observeTaskById(String taskId) {
        return db.taskDao().observeTaskById(taskId);
    }

    @Override
    public Single<List<Task>> getTasks() {
        return db.taskDao().getTasks();
    }

    @Override
    public Single<Task> getTaskById(String taskId) {
        return db.taskDao().getTaskById(taskId);
    }

    @Override
    public Completable insertTask(Task task) {
        return db.taskDao().insertTask(task);
    }

    @Override
    public Completable updateTask(Task task) {
        return db.taskDao().updateTask(task);
    }

    @Override
    public Completable updateCompleted(long taskId, boolean completed) {
        return db.taskDao().updateCompleted(taskId, completed);
    }

    @Override
    public Completable deleteTaskById(long taskId) {
        return db.taskDao().deleteTaskById(taskId);
    }

    @Override
    public Completable deleteTasks() {
        return db.taskDao().deleteTasks();
    }

    @Override
    public Completable deleteCompletedTasks() {
        return db.taskDao().deleteCompletedTasks();
    }
}
