package com.mobarak.todo.ui.tasks

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mobarak.todo.R
import com.mobarak.todo.databinding.TasksFragBinding
import com.mobarak.todo.utility.EventObserver
import com.mobarak.todo.utility.getViewModelFactory
import com.mobarak.todo.utility.setupRefreshLayout
import com.mobarak.todo.utility.setupSnackbar
import timber.log.Timber

class TasksFragment : Fragment() {
    private val viewModel by viewModels<TasksViewModel> { getViewModelFactory() }
    private lateinit var viewDataBinding: TasksFragBinding
    private val args: TasksFragmentArgs by navArgs()


    private lateinit var listAdapter: TasksAdapter
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = TasksFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.menu_clear -> {
                    viewModel.clearCompletedTasks()
                    true
                }
                R.id.menu_filter -> {
                    showFilteringPopUpMenu()
                    true
                }
                R.id.menu_refresh -> {
                    viewModel.loadTasks(true)
                    true
                }
                else -> false
            }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupSnackbar()
        setupListAdapter()
        setupRefreshLayout(viewDataBinding.refreshLayout, viewDataBinding.tasksList)
        setupNavigation()
//        setupFab()
    }

    private fun setupNavigation() {
        viewModel.openTaskEvent.observe(this.viewLifecycleOwner, EventObserver {
            openTaskDetails(it)
        })
        viewModel.newTaskEvent.observe(this.viewLifecycleOwner, EventObserver {
            navigateToAddNewTask()
        })
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
        arguments?.let {
            viewModel.showEditResultMessage(args.userMessage)
        }
    }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_tasks, menu)

            setOnMenuItemClickListener {
                viewModel.setFiltering(
                        when (it.itemId) {
                            R.id.active -> FilterType.ACTIVE_TASKS
                            R.id.completed -> FilterType.COMPLETED_TASKS
                            else -> FilterType.ALL_TASKS
                        }
                )
                true
            }
            show()
        }
    }

    private fun setupFab() {
        view?.findViewById<FloatingActionButton>(R.id.add_task_fab)?.let {
            it.setOnClickListener {
                navigateToAddNewTask()
            }
        }
    }

    private fun navigateToAddNewTask() {
        val action = TasksFragmentDirections
                .actionTasksFragmentToAddEditTaskFragment(
                        0,
                        resources.getString(R.string.add_task)
                )
        findNavController().navigate(action)
    }

    private fun openTaskDetails(taskId: Long) {
        val action = TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(taskId)
        findNavController().navigate(action)
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = TasksAdapter(viewModel)
            viewDataBinding.tasksList.adapter = listAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }
}