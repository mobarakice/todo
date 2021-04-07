package com.mobarak.todo.ui.statistics;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mobarak.todo.data.AppRepository;
import com.mobarak.todo.data.db.entity.Task;
import com.mobarak.todo.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StatisticsViewModel extends BaseViewModel {


    private static final String TAG = StatisticsFragment.class.getSimpleName();

    public StatisticsViewModel(Context context, AppRepository repository) {
        super(context, repository);
    }

    private MutableLiveData<List<Task>> tasks = new MutableLiveData<>(); /*tasksRepository.observeTasks()*/
    private MutableLiveData<StatsResult> stats = new MutableLiveData<>();
    public MutableLiveData<Float> activeTasksPercent = new MutableLiveData<>(0f);
    public MutableLiveData<Float> completedTasksPercent = new MutableLiveData<>(0f);


    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<Boolean>(false);

    public boolean empty() {
        return tasks != null && tasks.getValue() != null && tasks.getValue().isEmpty();
    }

    public void refresh() {
        dataLoading.setValue(true);
        mDisposable.add(repository.getDbRepository().observeTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                            tasks.setValue(items);
                            stats.setValue(StatisticsUtils.getActiveAndCompletedStats(tasks.getValue()));
                            dataLoading.setValue(false);
                            activeTasksPercent.setValue(stats.getValue().activeTasksPercent);
                            completedTasksPercent.setValue(stats.getValue().completedTasksPercent);
                            empty();
                        },
                        throwable -> {
                            Log.e(TAG, "no task found", throwable);
                        }));
    }
}
