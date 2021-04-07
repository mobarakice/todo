package com.mobarak.todo.ui.tasks

import android.content.Context
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.mobarak.todo.R
import com.mobarak.todo.data.AppRepository
import com.mobarak.todo.data.db.entity.Task
import com.mobarak.todo.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class TasksViewModel(context: Context?, repository: AppRepository?) : BaseViewModel(context, repository) {
    var filteredTasks = MutableLiveData<List<Task?>>()
    private val tasks = MutableLiveData<List<Task?>?>()
    var dataLoading = MutableLiveData<Boolean>()
    var empty = MutableLiveData<Boolean>()
    var currentFilteringLabel = MutableLiveData<Int>()
    var noTasksLabel = MutableLiveData<Int>()
    var noTaskIconRes = MutableLiveData<Int>()
    val snackbarText = MutableLiveData<String?>()
    fun setSnackbarText(message: String?) {
        snackbarText.value = message
    }

    fun empty(items: List<Task?>?) {
        empty.value = items == null || items.size <= 0
    }

    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be [TasksFilterType.ALL_TASKS],
     * [TasksFilterType.COMPLETED_TASKS], or
     * [TasksFilterType.ACTIVE_TASKS]
     */
    fun setFiltering(requestType: FilterType?) {
//        savedStateHandle.set(TASKS_FILTER_SAVED_STATE_KEY, requestType)

        // Depending on the filter type, set the filtering label, icon drawables, etc.
        when (requestType) {
            FilterType.ALL_TASKS -> setFilter(
                    R.string.label_all, R.string.no_tasks_all,
                    R.drawable.logo_no_fill
            )
            FilterType.ACTIVE_TASKS -> setFilter(
                    R.string.label_active, R.string.no_tasks_active,
                    R.drawable.ic_check_circle_96dp
            )
            FilterType.COMPLETED_TASKS -> setFilter(
                    R.string.label_completed, R.string.no_tasks_completed,
                    R.drawable.ic_verified_user_96dp
            )
        }
        // Refresh list
    }

    private fun setFilter(
            @StringRes filteringLabelString: Int,
            @StringRes noTasksLabelString: Int,
            @DrawableRes noTaskIconDrawable: Int
    ) {
        currentFilteringLabel.value = filteringLabelString
        noTasksLabel.value = noTasksLabelString
        noTaskIconRes.value = noTaskIconDrawable
    }

    fun completeTask(task: Task, completed: Boolean) {
        mDisposable!!.add(repository.dbRepository.updateCompleted(task.id, completed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.e(TAG, "no task found") }
                ) { throwable: Throwable? -> Log.e(TAG, "no task found", throwable) })
        if (completed) {
            setSnackbarText(context!!.getString(R.string.task_marked_complete))
        } else {
            setSnackbarText(context!!.getString(R.string.task_marked_active))
        }
    }

    fun loadTasks() {
        mDisposable!!.add(repository.dbRepository.observeTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tasks: List<Task?>? ->
                    this.tasks.value = tasks
                    filterItems(FilterType.ALL_TASKS)
                }
                ) { throwable: Throwable? ->
                    Log.e(TAG, "no task found", throwable)
                    empty(tasks.value)
                })
    }

    fun filterItems(filterType: FilterType?) {
        val filterTask: MutableList<Task?> = ArrayList()
        setFiltering(filterType)
        if (tasks.value != null) {
            when (filterType) {
                FilterType.ALL_TASKS -> filterTask.addAll(tasks.value!!)
                FilterType.ACTIVE_TASKS -> for (task in tasks.value!!) {
                    if (!task!!.isCompleted) {
                        filterTask.add(task)
                    }
                }
                FilterType.COMPLETED_TASKS -> for (task in tasks.value!!) {
                    if (task!!.isCompleted) {
                        filterTask.add(task)
                    }
                }
            }
        }
        filteredTasks.value = filterTask
        empty(filteredTasks.value)
    }

    fun openTask(view: View?, taskId: Long) {
        val action: NavDirections = TasksFragmentDirections
                .actionTasksFragmentToTaskDetailFragment(taskId)
        Navigation.findNavController(view!!).navigate(action)
    }

    fun refresh() {}

    companion object {
        private val TAG = TasksViewModel::class.java.simpleName
    }
}