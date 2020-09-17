package com.mobarak.todo.ui.taskdetail;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobarak.todo.R;
import com.mobarak.todo.data.AppRepository;
import com.mobarak.todo.data.db.entity.Task;

public class TaskDetailViewModel extends ViewModel {

    private AppRepository repository;
    private Context context;

    public TaskDetailViewModel(Context context, AppRepository repository) {
        this.context = context;
        this.repository = repository;
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
//        tasksRepository.deleteTask(it)
//        deleteTaskEvent.value = Event(Unit)
    }

    public void editTask() {
//        editTaskEvent.value = Event(Unit)
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
        if (dataLoading.getValue() || taskId == _taskId.getValue()) {
            return;
        }
        // Trigger the load
        _taskId.setValue(taskId);
    }

    private Task computeResult() {
        return null;
    }

    public void refresh() {
        // Refresh the repository and the task will be updated automatically.
        if (task != null && task.getValue() != null) {
//            dataLoading.setValue(true);
//            repository.refreshTask(_taskId.getValue());
//            dataLoading.setValue(true);
        }
    }

    private void showSnackbarMessage(String message) {
        snackbarText.setValue(message);
    }
}
