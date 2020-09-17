package com.mobarak.todo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;

import com.mobarak.todo.data.AppRepository;
import com.mobarak.todo.ui.addedittask.AddEditTaskViewModel;
import com.mobarak.todo.ui.statistics.StatisticsViewModel;
import com.mobarak.todo.ui.taskdetail.TaskDetailViewModel;
import com.mobarak.todo.ui.tasks.TasksViewModel;

public class ViewModelFactory extends AbstractSavedStateViewModelFactory {

    private AppRepository repository;
    private Context context;

    public ViewModelFactory(Context context, AppRepository repository, @NonNull SavedStateRegistryOwner owner) {
        super(owner, null);
        this.context = context;
        this.repository = repository;
    }

    @NonNull
    @Override
    protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {
        if (modelClass.equals(TasksViewModel.class)) {
            return (T) new TasksViewModel(context, repository);
        }if (modelClass.equals(StatisticsViewModel.class)) {
            return (T) new StatisticsViewModel(context, repository);
        }if (modelClass.equals(AddEditTaskViewModel.class)) {
            return (T) new AddEditTaskViewModel(context, repository);
        } if (modelClass.equals(TaskDetailViewModel.class)) {
            return (T) new TaskDetailViewModel(context, repository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}");
        }
    }
}
