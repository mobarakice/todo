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
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.mobarak.todo.R;
import com.mobarak.todo.ui.base.ViewModelFactory;
import com.mobarak.todo.data.AppRepositoryImpl;
import com.mobarak.todo.databinding.TasksFragBinding;
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
    public void onStart() {
        super.onStart();
        viewModel.loadTasks();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                viewModel.clearCompletedTasks();
                return true;

            case R.id.menu_filter:
                showFilteringPopUpMenu();
                return true;
            case R.id.menu_refresh:
                viewModel.loadTasks();
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
        View view = getActivity().findViewById(R.id.menu_filter);
        PopupMenu popup = new PopupMenu(getActivity(), view);
        popup.getMenuInflater().inflate(R.menu.filter_tasks, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> true);
        popup.show();
    }

    private void setupFab() {
        viewDataBinding.getRoot().findViewById(R.id.add_task_fab).setOnClickListener(view -> {
            NavDirections action = TasksFragmentDirections
                    .actionTasksFragmentToAddEditTaskFragment(
                            -1,
                            getActivity().getString(R.string.add_task)
                    );
            Navigation.findNavController(view).navigate(action);
        });
    }


    private void openTaskDetails(View view, long taskId) {
        NavDirections action = TasksFragmentDirections
                .actionTasksFragmentToTaskDetailFragment(taskId);
        Navigation.findNavController(view).navigate(action);
    }

    private void setupListAdapter() {
        if (viewModel != null) {
            listAdapter = new TasksAdapter(viewModel);
            viewDataBinding.tasksList.setAdapter(listAdapter);
        } else {
            Log.i("", "ViewModel not initialized when attempting to set up adapter.");
        }
    }

}