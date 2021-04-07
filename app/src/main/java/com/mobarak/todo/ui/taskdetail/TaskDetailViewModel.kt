package com.mobarak.todo.ui.taskdetail

import android.content.Context
import android.util.Log
import android.view.View
import com.mobarak.todo.data.db.entity.Task
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

class TaskDetailViewModel(context: Context?, repository: AppRepository?) : BaseViewModel(context, repository) {
    private val _taskId: MutableLiveData<Long> = MutableLiveData<Long>()
    var task: MutableLiveData<Task>? = MutableLiveData<Task>()
    var isDataAvailable: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var dataLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var completed: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var editTaskEvent: MutableLiveData<String> = MutableLiveData<String>()
    var deleteTaskEvent: MutableLiveData<String> = MutableLiveData<String>()
    var snackbarText: MutableLiveData<String> = MutableLiveData<String>()
    fun deleteTask() {
        if (_taskId.getValue() != null) mDisposable.add(repository.getDbRepository().deleteTaskById(_taskId.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Action { snackbarText.setValue(context.getString(R.string.menu_delete_task)) },
                        Consumer { throwable: Throwable? -> Log.e(TAG, "Task deleted", throwable) }))
    }

    fun editTask(view: View?) {
        if (_taskId.getValue() != null) {
            val action: NavDirections = TaskDetailFragmentDirections
                    .actionTaskDetailFragmentToAddEditTaskFragment(_taskId.getValue(), context.getString(R.string.add_task))
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun setCompleted(completed: Boolean) {
        val t: Task = task.getValue()
        if (completed) {
            //repository.completeTask(task);
            showSnackbarMessage(context.getString(R.string.task_marked_complete))
        } else {
            //tasksRepository.activateTask(task)
            showSnackbarMessage(context.getString(R.string.task_marked_active))
        }
    }

    fun start(taskId: Long) {
        // If we're already loading or already loaded, return (might be a config change)
        if (dataLoading.getValue() != null && dataLoading.getValue()
                || _taskId.getValue() != null && taskId == _taskId.getValue()) {
            return
        }
        // Trigger the load
        _taskId.setValue(taskId)
        loadTask()
    }

    private fun loadTask() {
        mDisposable.add(repository.getDbRepository().observeTaskById(_taskId.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer { item: Task? ->
                    task.setValue(item)
                    isDataAvailable.setValue(true)
                },
                        Consumer { throwable: Throwable? -> Log.e(TAG, "no task found", throwable) }))
    }

    private fun computeResult(): Task? {
        return null
    }

    fun refresh() {
        // Refresh the repository and the task will be updated automatically.
        if (task != null && task.getValue() != null) {
        }
    }

    private fun showSnackbarMessage(message: String) {
        snackbarText.setValue(message)
    }

    companion object {
        private val TAG = TaskDetailViewModel::class.java.simpleName
    }
}