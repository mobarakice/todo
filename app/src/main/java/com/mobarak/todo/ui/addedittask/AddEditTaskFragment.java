package com.mobarak.todo.ui.addedittask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.mobarak.todo.R;
import com.mobarak.todo.data.AppRepositoryImpl;
import com.mobarak.todo.databinding.AddtaskFragBinding;
import com.mobarak.todo.ui.base.ViewModelFactory;
import com.mobarak.todo.utility.EventObserver;
import com.mobarak.todo.utility.ViewUtil;

public class AddEditTaskFragment extends Fragment {

    private AddEditTaskViewModel viewModel;

    private AddtaskFragBinding viewDataBinding;
    private AddEditTaskFragmentArgs args;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addtask_frag, container, false);
        viewDataBinding = AddtaskFragBinding.bind(root);
        viewModel = new ViewModelProvider(this,
                new ViewModelFactory(getContext(), AppRepositoryImpl.getInstance(), this)
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
        viewModel.getSnackbarText().observe(getViewLifecycleOwner(), new EventObserver<>(event -> {
            ViewUtil.showSnackbar(viewDataBinding.getRoot(), (String) event.peekContent());
        }));
    }

    private void setupNavigation() {
        viewModel.getTaskUpdatedEvent().observe(getViewLifecycleOwner(), new EventObserver<>((event) -> {
            NavHostFragment.findNavController(this).popBackStack();
        }));
    }
}