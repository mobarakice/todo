package com.mobarak.todo.ui.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mobarak.todo.R
import com.mobarak.todo.databinding.AddtaskFragBinding
import com.mobarak.todo.databinding.TasksFragBinding
import com.mobarak.todo.ui.base.ADD_EDIT_RESULT_OK
import com.mobarak.todo.utility.*

class AddEditTaskFragment : Fragment() {
    private val viewModel by viewModels<AddEditTaskViewModel> { getViewModelFactory() }
    private lateinit var viewDataBinding: AddtaskFragBinding
    private val args by navArgs<AddEditTaskFragmentArgs>()
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.addtask_frag, container, false);
        viewDataBinding = AddtaskFragBinding.bind(root).apply {
            viewmodel = viewModel
        }
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSnackbar()
        setupNavigation()
        this.setupRefreshLayout(viewDataBinding.refreshLayout)
        viewModel.start(args.taskId)
    }


    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.taskUpdatedEvent.observe(this.viewLifecycleOwner, EventObserver {
            val action = AddEditTaskFragmentDirections
                    .actionAddEditTaskFragmentToTasksFragment(ADD_EDIT_RESULT_OK)
            findNavController().navigate(action)
        })
    }
}