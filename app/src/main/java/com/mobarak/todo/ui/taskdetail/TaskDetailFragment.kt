package com.mobarak.todo.ui.taskdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mobarak.todo.R
import com.mobarak.todo.databinding.TaskdetailFragBinding
import com.mobarak.todo.ui.base.DELETE_RESULT_OK
import com.mobarak.todo.utility.EventObserver
import com.mobarak.todo.utility.getViewModelFactory
import com.mobarak.todo.utility.setupRefreshLayout
import com.mobarak.todo.utility.setupSnackbar

class TaskDetailFragment : Fragment() {
    private lateinit var viewDataBinding: TaskdetailFragBinding

    private val args: TaskDetailFragmentArgs by navArgs()

    private val viewModel by viewModels<TaskDetailViewModel> { getViewModelFactory() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.taskdetail_frag, container, false)
        viewDataBinding = TaskdetailFragBinding.bind(view).apply {
            viewmodel = viewModel
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.start(args.taskId)

        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFab()
        view.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
        setupNavigation()
        this.setupRefreshLayout(viewDataBinding.refreshLayout)
    }

    private fun setupNavigation() {
        viewModel.deleteTaskEvent.observe(this.viewLifecycleOwner, EventObserver {
            val action = TaskDetailFragmentDirections
                    .actionTaskDetailFragmentToTasksFragment(DELETE_RESULT_OK)
            findNavController().navigate(action)
        })
        viewModel.editTaskEvent.observe(this.viewLifecycleOwner, EventObserver {
            val action = TaskDetailFragmentDirections
                    .actionTaskDetailFragmentToAddEditTaskFragment(
                            args.taskId,
                            resources.getString(R.string.edit_task)
                    )
            findNavController().navigate(action)
        })
    }

    private fun setupFab() {
        view?.findViewById<View>(R.id.edit_task_fab)?.setOnClickListener {
            viewModel.editTask()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                viewModel.deleteTask()
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.taskdetail_fragment_menu, menu)
    }
}