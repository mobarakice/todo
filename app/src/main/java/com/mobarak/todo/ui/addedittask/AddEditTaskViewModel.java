package com.mobarak.todo.ui.addedittask;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobarak.todo.R;
import com.mobarak.todo.data.AppRepository;
import com.mobarak.todo.data.db.entity.Task;
import com.mobarak.todo.ui.base.BaseViewModel;
import com.mobarak.todo.utility.Event;
import com.mobarak.todo.utility.Utility;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddEditTaskViewModel extends BaseViewModel {
    private static final String TAG = AddEditTaskViewModel.class.getSimpleName();

    public AddEditTaskViewModel(Context context, AppRepository repository) {
        super(context, repository);
    }

    // Two-way databinding, exposing MutableLiveData
    public MutableLiveData<String> title = new MutableLiveData<String>();
    public MutableLiveData<String> description = new MutableLiveData<String>();
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<Boolean>(false);


    private MutableLiveData<Event<String>> snackbarText = new MutableLiveData<>();

    private MutableLiveData<Event<Boolean>> taskUpdatedEvent = new MutableLiveData<>();

    public MutableLiveData<Event<String>> getSnackbarText() {
        return snackbarText;
    }

    private void setSnackbarText(String text) {
        this.snackbarText.setValue(new Event<>(text));
    }

    public MutableLiveData<Event<Boolean>> getTaskUpdatedEvent() {
        return taskUpdatedEvent;
    }

    public void setTaskUpdatedEvent(boolean update) {
        this.taskUpdatedEvent.setValue(new Event<>(update));
    }

    private Long taskId = null;

    private boolean isNewTask = false;

    private boolean isDataLoaded = false;

    private boolean taskCompleted = false;

    public void start(Long taskId) {
        if (dataLoading.getValue() != null && dataLoading.getValue()) {
            return;
        }

        this.taskId = taskId;
        if (taskId == null || taskId == -1) {
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
        loadTask(taskId);

    }

    private void loadTask(long taskId) {
        mDisposable.add(repository.getDbRepository().observeTaskById(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTaskLoaded,
                        throwable -> {
                            onDataNotAvailable();
                            Log.e(TAG, "no task found", throwable);
                        }));
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
            setSnackbarText(context.getString(R.string.empty_task_message));
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
        mDisposable.add(repository.getDbRepository().insertTask(newTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            setSnackbarText(context.getString(R.string.successfully_added_task_message));
                            setTaskUpdatedEvent(true);
                        },
                        throwable -> Log.e(TAG, "new task adding failed", throwable)));
    }

    private void updateTask(Task task) {
        if (isNewTask) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }
        mDisposable.add(repository.getDbRepository().updateTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            setSnackbarText(context.getString(R.string.successfully_updated_task_message));
                            setTaskUpdatedEvent(true);
                        },
                        throwable -> Log.e(TAG, "task updating failed", throwable)));
    }
}