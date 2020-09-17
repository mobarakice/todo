package com.mobarak.todo.ui.tasks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobarak.todo.R;
import com.mobarak.todo.ViewModelFactory;
import com.mobarak.todo.data.AppRepositoryImpl;
import com.mobarak.todo.databinding.TaskdetailFragBinding;
import com.mobarak.todo.databinding.TasksFragBinding;
import com.mobarak.todo.ui.taskdetail.TaskDetailViewModel;
import com.mobarak.todo.utility.ViewUtil;

public class TasksFragment extends Fragment {

    private TasksViewModel viewModel;
    private TasksFragBinding viewDataBinding;
    private TasksAdapter listAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tasks_frag, container, false);
        viewDataBinding = TasksFragBinding.bind(root);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(),
                new ViewModelFactory(getActivity(), AppRepositoryImpl.getInstance(), this)
        ).get(TasksViewModel.class);
//        viewModel.start(args.taskId)
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.setViewmodel(viewModel);
        ViewUtil.setupRefreshLayout(getActivity(), viewDataBinding.refreshLayout, null);
        setupFab();
        setupListAdapter();
        setupNavigation();
        setupSnackbar();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                viewModel.clearCompletedTasks();
                return true;

            case R.id.menu_filter:
                showFilteringPopUpMenu();
                return true;
            case R.id.menu_refresh:
                viewModel.loadTasks(true);
                return true;
            default:
                return false;
        }
    }

    private void setupNavigation() {
//        viewModel.openTaskEvent.observe(this, EventObserver {
//            openTaskDetails(it)
//        })
//        viewModel.newTaskEvent.observe(this, EventObserver {
//            navigateToAddNewTask()
//        })
    }

    private void setupSnackbar() {
//        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
//        arguments?.let {
//            viewModel.showEditResultMessage(args.userMessage)
//        }
    }

    private void showFilteringPopUpMenu() {
//        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
//                PopupMenu(requireContext(), view).run {
//            menuInflater.inflate(R.menu.filter_tasks, menu)
//
//            setOnMenuItemClickListener {
//                viewModel.setFiltering(
//                        when (it.itemId) {
//                    R.id.active -> TasksFilterType.ACTIVE_TASKS
//                    R.id.completed -> TasksFilterType.COMPLETED_TASKS
//                        else -> TasksFilterType.ALL_TASKS
//                }
//                )
//                true
//            }
//            show()
//        }
    }

    private void setupFab() {
    }

    private void navigateToAddNewTask() {

    }

    private void openTaskDetails(long taskId) {
//        val action = TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(taskId)
//        findNavController().navigate(action)
    }

    private void setupListAdapter() {
        if (viewModel != null) {
//            listAdapter = TasksAdapter(viewModel);
//            viewDataBinding.tasksList.adapter = listAdapter;
        } else {
            Log.i("","ViewModel not initialized when attempting to set up adapter.");
        }
    }

}