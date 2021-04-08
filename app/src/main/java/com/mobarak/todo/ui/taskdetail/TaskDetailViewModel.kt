package com.mobarak.todo.ui.taskdetail

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.mobarak.todo.R
import com.mobarak.todo.data.AppRepository
import com.mobarak.todo.data.db.entity.Task
import com.mobarak.todo.utility.Event
import kotlinx.coroutines.launch

class TaskDetailViewModel(private val repository: AppRepository) : ViewModel() {
    private val _taskId = MutableLiveData<Long>()

    private val _task = _taskId.switchMap { taskId ->
        repository.getDbRepository().observeTaskById(taskId).asLiveData().map {
            computeResult(it)
        }
    }
    val task: LiveData<Task?> = _task

    val isDataAvailable: LiveData<Boolean> = _task.map { it != null }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _editTaskEvent = MutableLiveData<Event<Unit>>()
    val editTaskEvent: LiveData<Event<Unit>> = _editTaskEvent

    private val _deleteTaskEvent = MutableLiveData<Event<Unit>>()
    val deleteTaskEvent: LiveData<Event<Unit>> = _deleteTaskEvent

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // This LiveData depends on another so we can use a transformation.
    val completed: LiveData<Boolean> = _task.map { input: Task? ->
        input?.isCompleted ?: false
    }

    fun deleteTask() = viewModelScope.launch {
        _taskId.value?.let {
            repository.getDbRepository().deleteTaskById(it)
            _deleteTaskEvent.value = Event(Unit)
        }
    }

    fun editTask() {
        _editTaskEvent.value = Event(Unit)
    }

    fun setCompleted(completed: Boolean) = viewModelScope.launch {
        val task = _task.value ?: return@launch
        if (completed) {
            repository.getDbRepository().updateTask(task)
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            repository.getDbRepository().updateTask(task)
            showSnackbarMessage(R.string.task_marked_active)
        }
    }

    fun start(taskId: Long) {
        // If we're already loading or already loaded, return (might be a config change)
        if (_dataLoading.value == true || taskId == _taskId.value) {
            return
        }
        // Trigger the load
        _taskId.value = taskId
    }

    private fun computeResult(task: Task): Task? {
        return if (task != null) {
            task
        } else {
            showSnackbarMessage(R.string.loading_tasks_error)
            null
        }
    }

    fun refresh() {
        // Refresh the repository and the task will be updated automatically.
        _task.value?.let {
            _dataLoading.value = true
            viewModelScope.launch {
                repository.getDbRepository().getTaskById(it.id)
                _dataLoading.value = false
            }
        }
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarText.value = Event(message)
    }

    companion object {
        private val TAG = TaskDetailViewModel::class.java.simpleName
    }
}