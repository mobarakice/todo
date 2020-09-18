package com.mobarak.todo.ui.tasks;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.DrawableRes;
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
        setFiltering(FilterType.ALL_TASKS);
    }

    private MutableLiveData<Boolean> _forceUpdate = new MutableLiveData<Boolean>(false);

    public MutableLiveData<List<Task>> items = new MutableLiveData<>();
    private MutableLiveData<List<Task>> _items = new MutableLiveData<>();

    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> empty = new MutableLiveData<Boolean>();

    public MutableLiveData<String> currentFilteringLabel = new MutableLiveData<>();
    public MutableLiveData<String> noTasksLabel = new MutableLiveData<>();

    public MutableLiveData<Integer> noTaskIconRes = new MutableLiveData<>();

    private MutableLiveData<Boolean> _tasksAddViewVisible = new MutableLiveData<Boolean>();

    public MutableLiveData<String> _snackbarText = new MutableLiveData<String>();

    public void empty() {
        empty.setValue(items.getValue() != null && items.getValue().size() <= 0);
    }


    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be [TasksFilterType.ALL_TASKS],
     *                    [TasksFilterType.COMPLETED_TASKS], or
     *                    [TasksFilterType.ACTIVE_TASKS]
     */
    private void setFiltering(FilterType requestType) {
//        savedStateHandle.set(TASKS_FILTER_SAVED_STATE_KEY, requestType)

        // Depending on the filter type, set the filtering label, icon drawables, etc.
        switch (requestType) {
            case ALL_TASKS:
                setFilter(
                        context.getString(R.string.label_all), context.getString(R.string.no_tasks_all),
                        R.drawable.logo_no_fill, true
                );
                break;
            case ACTIVE_TASKS:
                setFilter(
                        context.getString(R.string.label_active), context.getString(R.string.no_tasks_active),
                        R.drawable.ic_check_circle_96dp, false
                );
                break;
            case COMPLETED_TASKS:
                setFilter(
                        context.getString(R.string.label_completed), context.getString(R.string.no_tasks_completed),
                        R.drawable.ic_verified_user_96dp, false
                );
                break;
        }
        // Refresh list
        loadTasks();
    }

    private void setFilter(
            String filteringLabelString,
            String noTasksLabelString,
            @DrawableRes int noTaskIconDrawable,
            boolean tasksAddVisible
    ) {
        currentFilteringLabel.setValue(filteringLabelString);
        noTasksLabel.setValue(noTasksLabelString);
        noTaskIconRes.setValue(noTaskIconDrawable);
        _tasksAddViewVisible.setValue(tasksAddVisible);
    }


    public void completeTask(Task task, boolean completed) {

        mDisposable.add(repository.getDbRepository().updateCompleted(task.getId(), completed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.e(TAG, "no task found"),
                        throwable -> Log.e(TAG, "no task found", throwable)));

        if (completed) {
            showSnackbarMessage(context.getString(R.string.task_marked_complete));
        } else {
            //tasksRepository.activateTask(task)
            showSnackbarMessage(context.getString(R.string.task_marked_active));
        }
    }

    private void showSnackbarMessage(String message) {
        _snackbarText.setValue(message);
    }


    public void loadTasks() {
        mDisposable.add(repository.getDbRepository().observeTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                            _items.setValue(tasks);
                            filterItems(FilterType.ALL_TASKS);
                        },
                        throwable -> {
                            Log.e(TAG, "no task found", throwable);
                            empty();
                        }));
    }

    public void filterItems(FilterType filterType) {
        List<Task> filterTask = new ArrayList<>();
        switch (filterType) {
            case ALL_TASKS:
                filterTask.addAll(_items.getValue());
                break;
            case ACTIVE_TASKS:
                for (Task task : _items.getValue()) {
                    if (!task.isCompleted()) {
                        filterTask.add(task);
                    }
                }
                break;
            case COMPLETED_TASKS:
                for (Task task : _items.getValue()) {
                    if (task.isCompleted()) {
                        filterTask.add(task);
                    }
                }
                break;
        }

        items.setValue(filterTask);

    }

    public void openTask(View view, long taskId) {
        NavDirections action = TasksFragmentDirections
                .actionTasksFragmentToTaskDetailFragment(taskId);
        Navigation.findNavController(view).navigate(action);
    }

    public void refresh() {
        _forceUpdate.setValue(true);
    }

}
