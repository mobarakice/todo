package com.mobarak.todo.ui.statistics

import androidx.lifecycle.*
import com.mobarak.todo.data.AppRepository
import com.mobarak.todo.data.db.entity.Task
import kotlinx.coroutines.launch


class StatisticsViewModel(private val repository: AppRepository) : ViewModel() {
    private val tasks: LiveData<List<Task>> = repository.getDbRepository().observeTasks().asLiveData()
    private val _dataLoading = MutableLiveData<Boolean>(false)
    private val stats: LiveData<StatsResult?> = tasks.map {
        if (it.isNotEmpty()) {
            getActiveAndCompletedStats(it)
        } else {
            null
        }
    }

    val activeTasksPercent = stats.map {
        it?.activeTasksPercent ?: 0f
    }
    val completedTasksPercent: LiveData<Float> = stats.map { it?.completedTasksPercent ?: 0f }
    val dataLoading: LiveData<Boolean> = _dataLoading
    val error: LiveData<Boolean> = tasks.map { it.isNullOrEmpty() }
    val empty: LiveData<Boolean> = tasks.map { it.isNullOrEmpty() }

    fun refresh() {
        _dataLoading.value = true
        viewModelScope.launch {
            repository.getDbRepository().getTasks()
            _dataLoading.value = false
        }
    }

    companion object {
        private val TAG = StatisticsFragment::class.java.simpleName
    }
}