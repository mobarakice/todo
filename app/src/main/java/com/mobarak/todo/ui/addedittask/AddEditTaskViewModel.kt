package com.mobarak.todo.ui.addedittask

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mobarak.todo.R
import com.mobarak.todo.data.AppRepository
import com.mobarak.todo.data.db.entity.Task
import com.mobarak.todo.ui.base.BaseViewModel
import com.mobarak.todo.utility.Event
import com.mobarak.todo.utility.Utility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddEditTaskViewModel(context: Context?, repository: AppRepository?) : BaseViewModel(context, repository) {
    // Two-way databinding, exposing MutableLiveData
    var title = MutableLiveData<String?>()
    var description = MutableLiveData<String?>()
    var dataLoading = MutableLiveData(false)
    val snackbarText = MutableLiveData<Event<String>>()
    val taskUpdatedEvent = MutableLiveData<Event<Boolean>>()
    private fun setSnackbarText(text: String) {
        snackbarText.value = Event(text)
    }

    fun setTaskUpdatedEvent(update: Boolean) {
        taskUpdatedEvent.value = Event(update)
    }

    private var taskId: Long? = null
    private var isNewTask = false
    private var isDataLoaded = false
    private var taskCompleted = false
    fun start(taskId: Long?) {
        if (dataLoading.value != null && dataLoading.value!!) {
            return
        }
        this.taskId = taskId
        if (taskId == null || taskId == -1L) {
            // No need to populate, it's a new task
            isNewTask = true
            return
        }
        if (isDataLoaded) {
            // No need to populate, already have data.
            return
        }
        isNewTask = false
        dataLoading.value = true
        loadTask(taskId)
    }

    private fun loadTask(taskId: Long) {
        mDisposable!!.add(repository.dbRepository.observeTaskById(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ task: Task? -> onTaskLoaded(task) }
                ) { throwable: Throwable? ->
                    onDataNotAvailable()
                    Log.e(TAG, "no task found", throwable)
                })
    }

    private fun onTaskLoaded(task: Task?) {
        title.setValue(task.getTitle())
        description.setValue(task.getDescription())
        taskCompleted = task!!.isCompleted
        dataLoading.value = false
        isDataLoaded = true
    }

    private fun onDataNotAvailable() {
        dataLoading.value = false
    }

    // Called when clicking on fab.
    fun saveTask() {
        val currentTitle = title.value
        val currentDescription = description.value
        if (Utility.isNullOrEmpty(currentTitle) || Utility.isNullOrEmpty(currentDescription)) {
            setSnackbarText(context!!.getString(R.string.empty_task_message))
            return
        }
        val currentTaskId = taskId
        if (isNewTask || currentTaskId == null) {
            createTask(Task(currentTitle, currentDescription))
        } else {
            val task = Task(currentTaskId, currentTitle, currentDescription, taskCompleted)
            updateTask(task)
        }
    }

    private fun createTask(newTask: Task) {
        mDisposable!!.add(repository.dbRepository.insertTask(newTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setSnackbarText(context!!.getString(R.string.successfully_added_task_message))
                    setTaskUpdatedEvent(true)
                }
                ) { throwable: Throwable? -> Log.e(TAG, "new task adding failed", throwable) })
    }

    private fun updateTask(task: Task) {
        if (isNewTask) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        mDisposable!!.add(repository.dbRepository.updateTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setSnackbarText(context!!.getString(R.string.successfully_updated_task_message))
                    setTaskUpdatedEvent(true)
                }
                ) { throwable: Throwable? -> Log.e(TAG, "task updating failed", throwable) })
    }

    companion object {
        private val TAG = AddEditTaskViewModel::class.java.simpleName
    }
}