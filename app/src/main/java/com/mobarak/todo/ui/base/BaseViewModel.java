package com.mobarak.todo.ui.base;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.mobarak.todo.data.AppRepository;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    protected CompositeDisposable mDisposable = new CompositeDisposable();
    protected AppRepository repository;
    protected Context context;

    public BaseViewModel(Context context, AppRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @Override
    protected void onCleared() {
        if (mDisposable != null)
            mDisposable.dispose();
    }
}
