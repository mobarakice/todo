package com.mobarak.todo.ui.addedittask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.mobarak.todo.R;
import com.mobarak.todo.ui.base.ViewModelFactory;
import com.mobarak.todo.data.AppRepositoryImpl;
import com.mobarak.todo.databinding.AddtaskFragBinding;
import com.mobarak.todo.ui.tasks.TasksFragmentDirections;
import com.mobarak.todo.utility.Utility;
import com.mobarak.todo.utility.ViewUtil;

public class AddEditTaskFragment extends Fragment {

    private AddEditTaskViewModel viewModel;

    private AddtaskFragBinding viewDataBinding;
    private AddEditTaskFragmentArgs args;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addtask_frag, container, false);
        viewDataBinding = AddtaskFragBinding.bind(root);
        viewModel = new ViewModelProvider(requireActivity(),
                new ViewModelFactory(getActivity(), AppRepositoryImpl.getInstance(), this)
        ).get(AddEditTaskViewModel.class);
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.setViewmodel(viewModel);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getArguments() != null;
        args = AddEditTaskFragmentArgs.fromBundle(getArguments());
        setupNavigation();
        setupSnackbar();
        ViewUtil.setupRefreshLayout(requireActivity(), viewDataBinding.refreshLayout, null);
        viewModel.start(args.getTaskId());
    }


    private void setupSnackbar() {
        if (getActivity() != null)
            viewModel.getSnackbarText().observe(getActivity(), message -> {
                ViewUtil.showSnackbar(viewDataBinding.getRoot(), message);
            });
    }

    private void setupNavigation() {
        if (getActivity() != null)
            viewModel.taskUpdatedEvent.observe(getActivity(), status -> {
                if (status) {
                    NavDirections action = AddEditTaskFragmentDirections
                            .actionAddEditTaskFragmentToTasksFragment();
                    NavHostFragment.findNavController(this).navigate(action);
                }
            });
    }
}