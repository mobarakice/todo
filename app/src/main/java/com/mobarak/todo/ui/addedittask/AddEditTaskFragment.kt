package com.mobarak.todo.ui.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.mobarak.todo.R
import com.mobarak.todo.data.AppRepositoryImpl
import com.mobarak.todo.databinding.AddtaskFragBinding
import com.mobarak.todo.ui.base.ViewModelFactory
import com.mobarak.todo.utility.Event
import com.mobarak.todo.utility.EventObserver
import com.mobarak.todo.utility.ViewUtil

class AddEditTaskFragment : Fragment() {
    private var viewModel: AddEditTaskViewModel? = null
    private var viewDataBinding: AddtaskFragBinding? = null
    private var args: AddEditTaskFragmentArgs? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.addtask_frag, container, false)
        viewDataBinding = AddtaskFragBinding.bind(root)
        viewModel = ViewModelProvider(this,
                ViewModelFactory(context, AppRepositoryImpl.Companion.getInstance(), this)
        ).get(AddEditTaskViewModel::class.java)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.viewmodel = viewModel
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        assert(arguments != null)
        args = AddEditTaskFragmentArgs.fromBundle(arguments)
        setupNavigation()
        setupSnackbar()
        ViewUtil.setupRefreshLayout(requireActivity(), viewDataBinding!!.refreshLayout, null)
        viewModel!!.start(args.getTaskId())
    }

    private fun setupSnackbar() {
        viewModel.getSnackbarText().observe(viewLifecycleOwner, EventObserver { event: Event<*> -> ViewUtil.showSnackbar(viewDataBinding!!.root, event.peekContent() as String) })
    }

    private fun setupNavigation() {
        viewModel.getTaskUpdatedEvent().observe(viewLifecycleOwner, EventObserver { event: Event<*>? -> NavHostFragment.findNavController(this).popBackStack() })
    }
}