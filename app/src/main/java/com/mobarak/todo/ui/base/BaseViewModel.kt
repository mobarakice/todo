package com.mobarak.todo.ui.base;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.mobarak.todo.data.AppRepository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * This is base view model and it provides all common properties to child viewmodel
 *
 * @author mobarak
 */
public class BaseViewModel extends ViewModel {

    // Rxjava disposal
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
