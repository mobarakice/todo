package com.mobarak.todo.ui.taskdetail;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.mobarak.todo.R;
import com.mobarak.todo.data.AppRepository;
import com.mobarak.todo.data.db.entity.Task;
import com.mobarak.todo.ui.base.BaseViewModel;
import com.mobarak.todo.ui.tasks.FilterType;
import com.mobarak.todo.ui.tasks.TasksFragmentDirections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TaskDetailViewModel extends BaseViewModel {

    private static final String TAG = TaskDetailViewModel.class.getSimpleName();

    public TaskDetailViewModel(Context context, AppRepository repository) {
        super(context, repository);
    }

    private MutableLiveData<Long> _taskId = new MutableLiveData<Long>();

    public MutableLiveData<Task> task = new MutableLiveData<>();

    public MutableLiveData<Boolean> isDataAvailable = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> completed = new MutableLiveData<>(false);
    public MutableLiveData<String> editTaskEvent = new MutableLiveData<>();
    public MutableLiveData<String> deleteTaskEvent = new MutableLiveData<>();
    public MutableLiveData<String> snackbarText = new MutableLiveData<>();


    public void deleteTask() {
        if (_taskId.getValue() != null)
            mDisposable.add(repository.getDbRepository().deleteTaskById(_taskId.getValue())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                                snackbarText.setValue(context.getString(R.string.menu_delete_task));
                            },
                            throwable -> {
                                Log.e(TAG, "Task deleted", throwable);
                            }));
    }

    public void editTask(View view) {
        if (_taskId.getValue() != null) {
            NavDirections action = TaskDetailFragmentDirections
                    .actionTaskDetailFragmentToAddEditTaskFragment(_taskId.getValue(), context.getString(R.string.add_task));
            Navigation.findNavController(view).navigate(action);
        }
    }

    public void setCompleted(Boolean completed) {
        Task t = task.getValue();
        if (completed) {
            //repository.completeTask(task);
            showSnackbarMessage(context.getString(R.string.task_marked_complete));
        } else {
            //tasksRepository.activateTask(task)
            showSnackbarMessage(context.getString(R.string.task_marked_active));
        }
    }

    public void start(long taskId) {
        // If we're already loading or already loaded, return (might be a config change)
        if ((dataLoading.getValue() != null && dataLoading.getValue())
                || (_taskId.getValue() != null && taskId == _taskId.getValue())) {
            return;
        }
        // Trigger the load
        _taskId.setValue(taskId);
        loadTask();
    }

    private void loadTask() {
        mDisposable.add(repository.getDbRepository().observeTaskById(_taskId.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                            task.setValue(item);
                            isDataAvailable.setValue(true);
                        },
                        throwable -> {
                            Log.e(TAG, "no task found", throwable);
                        }));
    }

    private Task computeResult() {
        return null;
    }

    public void refresh() {
        // Refresh the repository and the task will be updated automatically.
        if (task != null && task.getValue() != null) {
        }
    }

    private void showSnackbarMessage(String message) {
        snackbarText.setValue(message);
    }
}
