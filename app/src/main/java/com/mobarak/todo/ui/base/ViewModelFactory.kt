package com.mobarak.todo.ui.base

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.mobarak.todo.data.AppRepository
import com.mobarak.todo.ui.addedittask.AddEditTaskViewModel
import com.mobarak.todo.ui.statistics.StatisticsViewModel
import com.mobarak.todo.ui.taskdetail.TaskDetailViewModel
import com.mobarak.todo.ui.tasks.TasksViewModel

/**
 * This view model factory class, all viewmodel will be instantiated via this class
 *
 * @author mobarak
 *//*

class ViewModelFactory(private val context: Context?, private val repository: AppRepository?, owner: SavedStateRegistryOwner)
    : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        if (modelClass == TasksViewModel::class.java) {
            return TasksViewModel(context, repository) as T
        }
        if (modelClass == StatisticsViewModel::class.java) {
            return StatisticsViewModel(context, repository) as T
        }
        if (modelClass == AddEditTaskViewModel::class.java) {
            return AddEditTaskViewModel(context, repository) as T
        }
        return if (modelClass == TaskDetailViewModel::class.java) {
            TaskDetailViewModel(context, repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: \${modelClass.name}")
        }
    }
}*/
/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
        private val tasksRepository: AppRepository,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(TasksViewModel::class.java) ->
                TasksViewModel(tasksRepository,handle)
            isAssignableFrom(StatisticsViewModel::class.java) ->
                StatisticsViewModel(tasksRepository)
            isAssignableFrom(AddEditTaskViewModel::class.java) ->
                AddEditTaskViewModel(tasksRepository)
            isAssignableFrom(TaskDetailViewModel::class.java) ->
                TaskDetailViewModel(tasksRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
