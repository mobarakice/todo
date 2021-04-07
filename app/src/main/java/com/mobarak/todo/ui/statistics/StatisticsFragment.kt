package com.mobarak.todo.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobarak.todo.R
import com.mobarak.todo.data.AppRepositoryImpl
import com.mobarak.todo.databinding.StatisticsFragBinding
import com.mobarak.todo.ui.base.ViewModelFactory
import com.mobarak.todo.utility.ViewUtil

class StatisticsFragment : Fragment() {
    private var viewModel: StatisticsViewModel? = null
    private var viewDataBinding: StatisticsFragBinding? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.statistics_frag, container, false)
        viewDataBinding = StatisticsFragBinding.bind(root)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
                ViewModelFactory(context, AppRepositoryImpl.Companion.getInstance(), this)
        ).get(StatisticsViewModel::class.java)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.viewmodel = viewModel
        ViewUtil.setupRefreshLayout(activity, viewDataBinding!!.refreshLayout, null)
        viewModel!!.refresh()
    }
}