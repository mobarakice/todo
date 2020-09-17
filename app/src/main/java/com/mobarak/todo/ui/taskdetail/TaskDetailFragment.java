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

import com.mobarak.todo.R;
import com.mobarak.todo.ViewModelFactory;
import com.mobarak.todo.data.AppRepositoryImpl;
import com.mobarak.todo.databinding.TaskdetailFragBinding;
import com.mobarak.todo.utility.ViewUtil;

public class TaskDetailFragment extends Fragment {

    private TaskDetailViewModel viewModel;
    private TaskdetailFragBinding viewDataBinding;

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
//        viewModel.start(args.taskId)
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.setViewmodel(viewModel);
        ViewUtil.setupRefreshLayout(getActivity(), viewDataBinding.refreshLayout, null);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.taskdetail_fragment_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_delete) {
            viewModel.deleteTask();
            return true;
        }

        return super.onContextItemSelected(item);
    }

}