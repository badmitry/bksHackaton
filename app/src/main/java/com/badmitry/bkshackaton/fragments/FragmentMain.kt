package com.badmitry.bkshackaton.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.badmitry.bkshackaton.MainActivity
import com.badmitry.bkshackaton.R
import com.badmitry.bkshackaton.databinding.FragmentMainBinding
import com.badmitry.bkshackaton.navigation.Screens
import com.badmitry.bkshackaton.viewmodels.MainFragmentViewModel
import javax.inject.Inject


class FragmentMain : BaseFragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOption.setOnClickListener { viewModel.navigateTo(Screens.OPTIONS) }
        binding.btnPortfolioGraph.setOnClickListener { viewModel.navigateTo(Screens.PORTFOLIO_GRAPH) }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, vmFactory)[MainFragmentViewModel::class.java]
        viewModel.observe(this, ::onProgress, ::onError)
        viewModel.liveData.observe(this, ::onDataChanged)
    }

    override fun setToolbar() {
        (requireActivity() as MainActivity).initToolbar(R.string.app_name, false)
    }

    private fun onDataChanged(int: Int) {

    }
}