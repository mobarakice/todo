package com.mobarak.todo.ui.statistics;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobarak.todo.data.AppRepository;
import com.mobarak.todo.data.db.entity.Task;
import com.mobarak.todo.ui.base.BaseViewModel;

import java.util.List;

public class StatisticsViewModel extends BaseViewModel {


    public StatisticsViewModel(Context context, AppRepository repository) {
        super(context, repository);
    }

    private LiveData<List<Task>> tasks = new MutableLiveData<>(); /*tasksRepository.observeTasks()*/
    private MutableLiveData<StatsResult> stats = new MutableLiveData<>(
            StatisticsUtils.getActiveAndCompletedStats(tasks.getValue()));

    public float activeTasksPercent = stats.getValue().activeTasksPercent;
    public float completedTasksPercent = stats.getValue().completedTasksPercent;

    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<Boolean>(false);

    public boolean empty() {
        return tasks != null && tasks.getValue() != null && tasks.getValue().isEmpty();
    }

    public void refresh() {
        dataLoading.setValue(true);
//        viewModelScope.launch {
//            dataLoading.setValue(false);
//        }
    }
}
