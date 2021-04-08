package com.mobarak.todo.ui.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.mobarak.todo.R
import com.mobarak.todo.data.AppRepository
import com.mobarak.todo.data.db.entity.Task
import com.mobarak.todo.ui.base.ADD_EDIT_RESULT_OK
import com.mobarak.todo.ui.base.DELETE_RESULT_OK
import com.mobarak.todo.ui.base.EDIT_RESULT_OK
import com.mobarak.todo.utility.Event
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.*

class TasksViewModel(private val repository: AppRepository,
                     private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _items: LiveData<List<Task>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                refresh()
                _dataLoading.value = false
            }
        }
        repository.getDbRepository()
                .observeTasks()
                .distinctUntilChanged()
                .asLiveData()
                .switchMap {
                    liveData {
                        filterItems(it, getSavedFilterType())
                    }
                }
    }

    val items: LiveData<List<Task>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _currentFilteringLabel = MutableLiveData<Int>()
    val currentFilteringLabel: LiveData<Int> = _currentFilteringLabel

    private val _noTasksLabel = MutableLiveData<Int>()
    val noTasksLabel: LiveData<Int> = _noTasksLabel

    private val _noTaskIconRes = MutableLiveData<Int>()
    val noTaskIconRes: LiveData<Int> = _noTaskIconRes

    private val _tasksAddViewVisible = MutableLiveData<Boolean>()
    val tasksAddViewVisible: LiveData<Boolean> = _tasksAddViewVisible

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // Not used at the moment
    private val isDataLoadingError = MutableLiveData<Boolean>()

    private val _openTaskEvent = MutableLiveData<Event<Long>>()
    val openTaskEvent: LiveData<Event<Long>> = _openTaskEvent

    private val _newTaskEvent = MutableLiveData<Event<Unit>>()
    val newTaskEvent: LiveData<Event<Unit>> = _newTaskEvent

    private var resultMessageShown: Boolean = false

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    init {
        // Set initial state
        setFiltering(getSavedFilterType())
        loadTasks(true)
    }

    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be [TasksFilterType.ALL_TASKS],
     * [TasksFilterType.COMPLETED_TASKS], or
     * [TasksFilterType.ACTIVE_TASKS]
     */
    fun setFiltering(requestType: FilterType) {
        savedStateHandle.set(TASKS_FILTER_SAVED_STATE_KEY, requestType)

        // Depending on the filter type, set the filtering label, icon drawables, etc.
        when (requestType) {
            FilterType.ALL_TASKS -> {
                setFilter(
                        R.string.label_all, R.string.no_tasks_all,
                        R.drawable.logo_no_fill, true
                )
            }
            FilterType.ACTIVE_TASKS -> {
                setFilter(
                        R.string.label_active, R.string.no_tasks_active,
                        R.drawable.ic_check_circle_96dp, false
                )
            }
            FilterType.COMPLETED_TASKS -> {
                setFilter(
                        R.string.label_completed, R.string.no_tasks_completed,
                        R.drawable.ic_verified_user_96dp, false
                )
            }
        }
        // Refresh list
        loadTasks(false)
    }

    private fun setFilter(
            @StringRes filteringLabelString: Int,
            @StringRes noTasksLabelString: Int,
            @DrawableRes noTaskIconDrawable: Int,
            tasksAddVisible: Boolean
    ) {
        _currentFilteringLabel.value = filteringLabelString
        _noTasksLabel.value = noTasksLabelString
        _noTaskIconRes.value = noTaskIconDrawable
        _tasksAddViewVisible.value = tasksAddVisible
    }

    /**
     * @param forceUpdate Pass in true to refresh the data in the [TasksDataSource]
     */
    fun loadTasks(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    private fun filterItems(tasks: List<Task>, filteringType: FilterType): List<Task> {
        val tasksToShow = ArrayList<Task>()
        // We filter the tasks based on the requestType
        for (task in tasks) {
            when (filteringType) {
                FilterType.ALL_TASKS -> tasksToShow.add(task)
                FilterType.ACTIVE_TASKS -> if (task.isActive) {
                    tasksToShow.add(task)
                }
                FilterType.COMPLETED_TASKS -> if (task.isCompleted) {
                    tasksToShow.add(task)
                }
            }
        }
        return tasksToShow
    }

    fun refresh() {
        _forceUpdate.value = true
    }

    private fun getSavedFilterType(): FilterType {
        return savedStateHandle.get(TASKS_FILTER_SAVED_STATE_KEY) ?: FilterType.ALL_TASKS
    }

    companion object {
        private val TAG = TasksViewModel::class.java.simpleName
    }

    fun clearCompletedTasks() {
        viewModelScope.launch {
            repository.getDbRepository().deleteCompletedTasks()
            showSnackbarMessage(R.string.completed_tasks_cleared)
        }
    }

    fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {
        if (completed) {
            repository.getDbRepository().updateTask(task)
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            repository.getDbRepository().updateCompleted(task.id, completed)
            showSnackbarMessage(R.string.task_marked_active)
        }
    }

    /**
     * Called by the Data Binding library and the FAB's click listener.
     */
    fun addNewTask() {
        _newTaskEvent.value = Event(Unit)
    }

    /**
     * Called by Data Binding.
     */
    fun openTask(taskId: Long) {
        _openTaskEvent.value = Event(taskId)
    }

    fun showEditResultMessage(result: Int) {
        if (resultMessageShown) return
        when (result) {
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_saved_task_message)
            ADD_EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_added_task_message)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_deleted_task_message)
        }
        resultMessageShown = true
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
}

// Used to save the current filtering in SavedStateHandle.
const val TASKS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"