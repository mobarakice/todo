package com.mobarak.todo.data.db;

import com.mobarak.todo.data.db.entity.Task;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DbRepository {
    Flowable<List<Task>> observeTasks();

    Flowable<Task> observeTaskById(String taskId);

    Single<List<Task>> getTasks();

    Single<Task> getTaskById(String taskId);

    Completable insertTask(Task task);

    Completable updateTask(Task task);

    Completable updateCompleted(long taskId, boolean completed);

    Completable deleteTaskById(long taskId);

    Completable deleteTasks();

    Completable deleteCompletedTasks();
}
