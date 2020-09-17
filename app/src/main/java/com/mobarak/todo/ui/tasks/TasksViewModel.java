package com.mobarak.todo.ui.tasks;

import android.content.Context;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobarak.todo.R;
import com.mobarak.todo.data.AppRepository;
import com.mobarak.todo.data.db.entity.Task;
import com.mobarak.todo.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TasksViewModel extends BaseViewModel {

    private static final String TASKS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY";
    private static final String TAG = TasksViewModel.class.getSimpleName();

    public TasksViewModel(Context context, AppRepository repository) {
        super(context, repository);
        setFiltering(FilterType.ALL_TASKS);
    }

    private MutableLiveData<Boolean> _forceUpdate = new MutableLiveData<Boolean>(false);

    public MutableLiveData<List<Task>> items = new MutableLiveData<>();

    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> empty = new MutableLiveData<Boolean>();

    public MutableLiveData<String> currentFilteringLabel = new MutableLiveData<>();
    public MutableLiveData<String> noTasksLabel = new MutableLiveData<>();

    public MutableLiveData<Integer> noTaskIconRes = new MutableLiveData<>();

    private MutableLiveData<Boolean> _tasksAddViewVisible = new MutableLiveData<Boolean>();

    public MutableLiveData<String> _snackbarText = new MutableLiveData<String>();

    // Not used at the moment
    private MutableLiveData<Boolean> isDataLoadingError = new MutableLiveData<Boolean>();

    private MutableLiveData<String> _openTaskEvent = new MutableLiveData<String>();


    private boolean resultMessageShown = false;

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

    public void clearCompletedTasks() {
        // repository.getDbRepository().clearCompletedTasks();
        //showSnackbarMessage(R.string.completed_tasks_cleared)
    }

    public void completeTask(Task task, boolean completed) {

        if (completed) {
            //tasksRepository.completeTask(task)
            showSnackbarMessage(context.getString(R.string.task_marked_complete));
        } else {
            //tasksRepository.activateTask(task)
            showSnackbarMessage(context.getString(R.string.task_marked_active));
        }
    }

    /**
     * Called by the Data Binding library and the FAB's click listener.
     */
    public void addNewTask() {

    }

    /**
     * Called by Data Binding.
     */
    public void openTask(long taskId) {

    }

//    fun showEditResultMessage(result:Int) {
//        if (resultMessageShown) return
//                when(result) {
//            EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_saved_task_message)
//            ADD_EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_added_task_message)
//            DELETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_deleted_task_message)
//        }
//        resultMessageShown = true
//    }

    private void showSnackbarMessage(String message) {
        _snackbarText.setValue(message);
    }

//    private fun filterTasks(tasksResult:Result<List<Task>>):LiveData<List<Task>>
//
//    {
//        // TODO: This is a good case for liveData builder. Replace when stable.
//        val result = MutableLiveData < List < Task >> ()
//
//        if (tasksResult is Success){
//        isDataLoadingError.value = false
//        viewModelScope.launch {
//            result.value = filterItems(tasksResult.data, getSavedFilterType())
//        }
//    } else{
//        result.value = emptyList()
//        showSnackbarMessage(R.string.loading_tasks_error)
//        isDataLoadingError.value = true
//    }
//
//        return result
//    }


    public void loadTasks() {
        mDisposable.add(repository.getDbRepository().observeTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> items.setValue(tasks),
                        throwable -> {
                            Log.e(TAG, "no task found", throwable);
                            empty();
                        }));
    }

//    private fun filterItems(tasks:List<Task>, filteringType:TasksFilterType):List<Task>
//
//    {
//        val tasksToShow = ArrayList < Task > ()
//        // We filter the tasks based on the requestType
//        for (task in tasks) {
//            when(filteringType) {
//                ALL_TASKS -> tasksToShow.add(task)
//                ACTIVE_TASKS -> if (task.isActive) {
//                    tasksToShow.add(task)
//                }
//                COMPLETED_TASKS -> if (task.isCompleted) {
//                    tasksToShow.add(task)
//                }
//            }
//        }
//        return tasksToShow
//    }

    public void refresh() {
        _forceUpdate.setValue(true);
    }

//    private FilterType getSavedFilterType()  {
//        return savedStateHandle.get(TASKS_FILTER_SAVED_STATE_KEY) ?: ALL_TASKS;
//    }

}
