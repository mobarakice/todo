package com.mobarak.todo.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mobarak.todo.R
import com.mobarak.todo.databinding.StatisticsFragBinding
import com.mobarak.todo.utility.getViewModelFactory
import com.mobarak.todo.utility.setupRefreshLayout

class StatisticsFragment : Fragment() {
    private val viewModel by viewModels<StatisticsViewModel> { getViewModelFactory() }
    private lateinit var viewDataBinding: StatisticsFragBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = DataBindingUtil.inflate(
                inflater, R.layout.statistics_frag, container,
                false
        )
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel = viewModel
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        this.setupRefreshLayout(viewDataBinding.refreshLayout)
    }
}