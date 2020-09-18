package com.mobarak.todo.ui.taskdetail;

import android.os.Bundle;
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
import androidx.navigation.fragment.NavHostFragment;

import com.mobarak.todo.R;
import com.mobarak.todo.ui.addedittask.AddEditTaskFragmentArgs;
import com.mobarak.todo.ui.base.ViewModelFactory;
import com.mobarak.todo.data.AppRepositoryImpl;
import com.mobarak.todo.databinding.TaskdetailFragBinding;
import com.mobarak.todo.utility.ViewUtil;

public class TaskDetailFragment extends Fragment {

    private TaskDetailViewModel viewModel;
    private TaskdetailFragBinding viewDataBinding;
    private TaskDetailFragmentArgs args;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.taskdetail_frag, container, false);
        viewDataBinding = TaskdetailFragBinding.bind(root);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(),
                new ViewModelFactory(getActivity(), AppRepositoryImpl.getInstance(), this)
        ).get(TaskDetailViewModel.class);
        if (getArguments() != null) {
            viewModel.start(TaskDetailFragmentArgs.fromBundle(getArguments()).getTaskId());
        }
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.setViewmodel(viewModel);
        ViewUtil.setupRefreshLayout(getActivity(), viewDataBinding.refreshLayout, null);
        setupSnackbar();

    }

    private void setupSnackbar() {
        if (getActivity() != null)
            viewModel.snackbarText.observe(getActivity(), message -> {
                ViewUtil.showSnackbar(viewDataBinding.getRoot(), message);
                NavHostFragment.findNavController(this).popBackStack();
            });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.taskdetail_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_delete) {
            viewModel.deleteTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}