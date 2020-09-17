package com.mobarak.todo.ui.addedittask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.mobarak.todo.R;
import com.mobarak.todo.ViewModelFactory;
import com.mobarak.todo.data.AppRepositoryImpl;
import com.mobarak.todo.databinding.AddtaskFragBinding;
import com.mobarak.todo.ui.home.HomeViewModel;
import com.mobarak.todo.utility.ViewUtil;

public class AddEditTaskFragment extends Fragment {

    private AddEditTaskViewModel viewModel;

    private AddtaskFragBinding viewDataBinding;


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
//        setupNavigation();
        ViewUtil.setupRefreshLayout(requireActivity(), viewDataBinding.refreshLayout, null);
//        viewModel.start(args.taskId)
    }


//    private fun setupNavigation() {
//        viewModel.taskUpdatedEvent.observe(this, EventObserver {
//            val action = AddEditTaskFragmentDirections
//                    .actionAddEditTaskFragmentToTasksFragment(ADD_EDIT_RESULT_OK)
//            findNavController().navigate(action)
//        })
//    }
}