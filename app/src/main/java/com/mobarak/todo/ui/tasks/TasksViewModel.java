package com.mobarak.todo.ui.tasks;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.mobarak.todo.R;
import com.mobarak.todo.data.AppRepository;
import com.mobarak.todo.data.db.entity.Task;
import com.mobarak.todo.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TasksViewModel extends BaseViewModel {

    private static final String TAG = TasksViewModel.class.getSimpleName();

    public TasksViewModel(Context context, AppRepository repository) {
        super(context, repository);
    }


    public MutableLiveData<List<Task>> filteredTasks = new MutableLiveData<>();
    private MutableLiveData<List<Task>> tasks = new MutableLiveData<>();

    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> empty = new MutableLiveData<>();

    public MutableLiveData<Integer> currentFilteringLabel = new MutableLiveData<>();
    public MutableLiveData<Integer> noTasksLabel = new MutableLiveData<>();

    public MutableLiveData<Integer> noTaskIconRes = new MutableLiveData<>();

    private MutableLiveData<String> snackbarText = new MutableLiveData<String>();

    public MutableLiveData<String> getSnackbarText() {
        return snackbarText;
    }

    public void setSnackbarText(String message) {
        snackbarText.setValue(message);
    }

    public void empty(List<Task> items) {
        empty.setValue(items == null || items.size() <= 0);
    }


    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be [TasksFilterType.ALL_TASKS],
     *                    [TasksFilterType.COMPLETED_TASKS], or
     *                    [TasksFilterType.ACTIVE_TASKS]
     */
    public void setFiltering(FilterType requestType) {
//        savedStateHandle.set(TASKS_FILTER_SAVED_STATE_KEY, requestType)

        // Depending on the filter type, set the filtering label, icon drawables, etc.
        switch (requestType) {
            case ALL_TASKS:
                setFilter(
                        R.string.label_all, R.string.no_tasks_all,
                        R.drawable.logo_no_fill
                );
                break;
            case ACTIVE_TASKS:
                setFilter(
                        R.string.label_active, R.string.no_tasks_active,
                        R.drawable.ic_check_circle_96dp
                );
                break;
            case COMPLETED_TASKS:
                setFilter(
                        R.string.label_completed, R.string.no_tasks_completed,
                        R.drawable.ic_verified_user_96dp
                );
                break;
        }
        // Refresh list
    }

    private void setFilter(
            @StringRes int filteringLabelString,
            @StringRes int noTasksLabelString,
            @DrawableRes int noTaskIconDrawable
    ) {
        currentFilteringLabel.setValue(filteringLabelString);
        noTasksLabel.setValue(noTasksLabelString);
        noTaskIconRes.setValue(noTaskIconDrawable);
    }


    public void completeTask(Task task, boolean completed) {

        mDisposable.add(repository.getDbRepository().updateCompleted(task.getId(), completed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.e(TAG, "no task found"),
                        throwable -> Log.e(TAG, "no task found", throwable)));

        if (completed) {
            setSnackbarText(context.getString(R.string.task_marked_complete));
        } else {
            setSnackbarText(context.getString(R.string.task_marked_active));
        }
    }


    public void loadTasks() {
        mDisposable.add(repository.getDbRepository().observeTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                            this.tasks.setValue(tasks);
                            filterItems(FilterType.ALL_TASKS);
                        },
                        throwable -> {
                            Log.e(TAG, "no task found", throwable);
                            empty(tasks.getValue());
                        }));
    }

    public void filterItems(FilterType filterType) {
        List<Task> filterTask = new ArrayList<>();
        setFiltering(filterType);
        switch (filterType) {
            case ALL_TASKS:
                filterTask.addAll(tasks.getValue());
                break;
            case ACTIVE_TASKS:
                for (Task task : tasks.getValue()) {
                    if (!task.isCompleted()) {
                        filterTask.add(task);
                    }
                }
                break;
            case COMPLETED_TASKS:
                for (Task task : tasks.getValue()) {
                    if (task.isCompleted()) {
                        filterTask.add(task);
                    }
                }
                break;
        }

        filteredTasks.setValue(filterTask);
        empty(filteredTasks.getValue());

    }

    public void openTask(View view, long taskId) {
        NavDirections action = TasksFragmentDirections
                .actionTasksFragmentToTaskDetailFragment(taskId);
        Navigation.findNavController(view).navigate(action);
    }

    public void refresh() {
    }

}
