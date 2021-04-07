package com.mobarak.todo.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.mobarak.todo.R
import com.mobarak.todo.data.AppRepositoryImpl
import com.mobarak.todo.databinding.TasksFragBinding
import com.mobarak.todo.ui.base.ViewModelFactory
import com.mobarak.todo.utility.ViewUtil

class TasksFragment : Fragment() {
    private var viewModel: TasksViewModel? = null
    private var viewDataBinding: TasksFragBinding? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.tasks_frag, container, false)
        viewDataBinding = TasksFragBinding.bind(root)
        setHasOptionsMenu(true)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
                ViewModelFactory(context, AppRepositoryImpl.Companion.getInstance(), this)
        ).get(TasksViewModel::class.java)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.viewmodel = viewModel
        ViewUtil.setupRefreshLayout(activity, viewDataBinding!!.refreshLayout, null)
        setupFab()
        setupListAdapter()
        setupSnackbar()
    }

    private fun setupSnackbar() {
        if (activity != null) viewModel.getSnackbarText().observe(activity, Observer { message: String? -> ViewUtil.showSnackbar(viewDataBinding!!.root, message) })
    }

    override fun onStart() {
        super.onStart()
        viewModel!!.setFiltering(FilterType.ALL_TASKS)
        viewModel!!.loadTasks()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear -> //                viewModel.clearCompletedTasks();
                true
            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            R.id.menu_refresh -> {
                viewModel!!.loadTasks()
                true
            }
            else -> false
        }
    }

    private fun showFilteringPopUpMenu() {
        val view = activity!!.findViewById<View>(R.id.menu_filter)
        val popup = PopupMenu(activity!!, view)
        popup.menuInflater.inflate(R.menu.filter_tasks, popup.menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.active -> {
                    viewModel!!.filterItems(FilterType.ACTIVE_TASKS)
                    return@setOnMenuItemClickListener true
                }
                R.id.completed -> {
                    viewModel!!.filterItems(FilterType.COMPLETED_TASKS)
                    return@setOnMenuItemClickListener true
                }
                R.id.all -> {
                    viewModel!!.filterItems(FilterType.ALL_TASKS)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
    }

    private fun setupFab() {
        viewDataBinding!!.root.findViewById<View>(R.id.add_task_fab).setOnClickListener { view: View? ->
            val action: NavDirections = TasksFragmentDirections
                    .actionTasksFragmentToAddEditTaskFragment(-1, activity!!.getString(R.string.add_task))
            Navigation.findNavController(view!!).navigate(action)
        }
    }

    private fun setupListAdapter() {
        if (viewModel != null) {
            val listAdapter = TasksAdapter(viewModel!!)
            viewDataBinding!!.tasksList.adapter = listAdapter
        } else {
            Log.i("", "ViewModel not initialized when attempting to set up adapter.")
        }
    }
}