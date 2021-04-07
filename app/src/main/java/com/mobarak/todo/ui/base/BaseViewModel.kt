package com.mobarak.todo.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobarak.todo.data.AppRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * This is base view model and it provides all common properties to child viewmodel
 *
 * @author mobarak
 */
open class BaseViewModel(protected var context: Context?, protected var repository: AppRepository?) : ViewModel() {
    // Rxjava disposal
    protected var mDisposable: CompositeDisposable? = CompositeDisposable()
    override fun onCleared() {
        if (mDisposable != null) mDisposable!!.dispose()
    }
}