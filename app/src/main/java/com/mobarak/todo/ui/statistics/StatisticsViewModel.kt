package com.mobarak.todo.ui.statistics

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mobarak.todo.data.AppRepository
import com.mobarak.todo.data.db.entity.Task
import com.mobarak.todo.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StatisticsViewModel(context: Context?, repository: AppRepository?) : BaseViewModel(context, repository) {
    private val tasks: MutableLiveData<List<Task?>?>? = MutableLiveData() /*tasksRepository.observeTasks()*/
    private val stats = MutableLiveData<StatsResult?>()
    var activeTasksPercent = MutableLiveData(0f)
    var completedTasksPercent = MutableLiveData(0f)
    var dataLoading = MutableLiveData(false)
    fun empty(): Boolean {
        return tasks != null && tasks.value != null && tasks.value!!.isEmpty()
    }

    fun refresh() {
        dataLoading.value = true
        mDisposable!!.add(repository.dbRepository.observeTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items: List<Task?>? ->
                    tasks!!.value = items
                    stats.value = StatisticsUtils.getActiveAndCompletedStats(tasks.value)
                    dataLoading.value = false
                    activeTasksPercent.value = stats.value!!.activeTasksPercent
                    completedTasksPercent.value = stats.value!!.completedTasksPercent
                    empty()
                }
                ) { throwable: Throwable? -> Log.e(TAG, "no task found", throwable) })
    }

    companion object {
        private val TAG = StatisticsFragment::class.java.simpleName
    }
}