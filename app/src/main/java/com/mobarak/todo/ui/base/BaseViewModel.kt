package com.mobarak.todo.ui.base


import androidx.lifecycle.ViewModel
import com.mobarak.todo.data.AppRepository


/**
 * This is base view model and it provides all common properties to child viewmodel
 *
 * @author mobarak
 */
open class BaseViewModel(protected val repository: AppRepository) : ViewModel()