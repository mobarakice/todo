package com.mobarak.todo.ui.addedittask;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobarak.todo.R;
import com.mobarak.todo.data.AppRepository;
import com.mobarak.todo.data.db.entity.Task;
import com.mobarak.todo.utility.Utility;

public class AddEditTaskViewModel extends ViewModel {
    private AppRepository repository;
    private Context context;

    public AddEditTaskViewModel(Context context, AppRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    // Two-way databinding, exposing MutableLiveData
    public MutableLiveData<String> title = new MutableLiveData<String>();
    public MutableLiveData<String> description = new MutableLiveData<String>();
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<Boolean>();

    public MutableLiveData<String> _snackbarText = new MutableLiveData<String>();

    public MutableLiveData<String> _taskUpdatedEvent = new MutableLiveData<String>();


    private Long taskId = null;

    private boolean isNewTask = false;

    private boolean isDataLoaded = false;

    private boolean taskCompleted = false;

    private void start(Long taskId) {
        if (dataLoading.getValue()) {
            return;
        }

        this.taskId = taskId;
        if (taskId == null) {
            // No need to populate, it's a new task
            isNewTask = true;
            return;
        }
        if (isDataLoaded) {
            // No need to populate, already have data.
            return;
        }

        isNewTask = false;
        dataLoading.setValue(true);

//        viewModelScope.launch {
//            tasksRepository.getTask(taskId).let { result ->
//                if (result is Success) {
//                    onTaskLoaded(result.data)
//                } else {
//                    onDataNotAvailable()
//                }
//            }
//        }
    }

    private void onTaskLoaded(Task task) {
        title.setValue(task.getTitle());
        description.setValue(task.getDescription());
        taskCompleted = task.isCompleted();
        dataLoading.setValue(false);
        isDataLoaded = true;
    }

    private void onDataNotAvailable() {
        dataLoading.setValue(false);
    }

    // Called when clicking on fab.
    public void saveTask() {
        String currentTitle = title.getValue();
        String currentDescription = description.getValue();

        if (Utility.isNullOrEmpty(currentTitle) || Utility.isNullOrEmpty(currentDescription)) {
            _snackbarText.setValue(context.getString(R.string.empty_task_message));
            return;
        }

        Long currentTaskId = taskId;
        if (isNewTask || currentTaskId == null) {
            createTask(new Task(currentTitle, currentDescription));
        } else {
            Task task = new Task(currentTaskId, currentTitle, currentDescription, taskCompleted);
            updateTask(task);
        }
    }

    private void createTask(Task newTask) {
//        tasksRepository.saveTask(newTask);
//        _taskUpdatedEvent.value = Event(Unit)
    }

    private void updateTask(Task task) {
        if (isNewTask) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }
//        viewModelScope.launch {
//            tasksRepository.saveTask(task)
//            _taskUpdatedEvent.value = Event(Unit)
//        }
    }
}