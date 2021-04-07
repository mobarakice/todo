package com.mobarak.todo.ui.taskdetail

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mobarak.todo.databinding.TaskdetailFragBinding

class TaskDetailFragment : Fragment() {
    private var viewModel: TaskDetailViewModel? = null
    private var viewDataBinding: TaskdetailFragBinding? = null
    private val args: TaskDetailFragmentArgs? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.taskdetail_frag, container, false)
        viewDataBinding = TaskdetailFragBinding.bind(root)
        setHasOptionsMenu(true)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
                ViewModelFactory(context, AppRepositoryImpl.Companion.getInstance(), this)
        ).get<TaskDetailViewModel>(TaskDetailViewModel::class.java)
        if (arguments != null) {
            viewModel!!.start(TaskDetailFragmentArgs.fromBundle(arguments).getTaskId())
        }
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.viewmodel = viewModel
        ViewUtil.setupRefreshLayout(activity, viewDataBinding!!.refreshLayout, null)
        setupSnackbar()
    }

    private fun setupSnackbar() {
        if (activity != null) viewModel!!.snackbarText.observe(activity, Observer { message: String? ->
            ViewUtil.showSnackbar(viewDataBinding!!.root, message)
            NavHostFragment.findNavController(this).popBackStack()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.taskdetail_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            viewModel!!.deleteTask()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}