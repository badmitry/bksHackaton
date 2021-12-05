package com.badmitry.bkshackaton.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.badmitry.bkshackaton.MainActivity
import com.badmitry.bkshackaton.R
import com.badmitry.bkshackaton.databinding.FragmentPortfolioGraphBinding
import com.badmitry.bkshackaton.navigation.Screens
import com.badmitry.bkshackaton.rvadapters.PortfolioGraphRVAdapter
import com.badmitry.bkshackaton.view.SubFragmentSaver
import com.badmitry.bkshackaton.viewmodels.PortfolioGraphViewModel
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


class FragmentPortfolioGraph : BaseFragment(), SubFragmentSaver {
    private lateinit var binding: FragmentPortfolioGraphBinding
    private lateinit var viewModel: PortfolioGraphViewModel
    private lateinit var adapter: PortfolioGraphRVAdapter
    private var titles: Array<out String>? = null
    private var screenNumber: Int = 0

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
        binding = FragmentPortfolioGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titles = requireContext().resources.getStringArray(R.array.portfolio_graph_titles)
        val navigator =
            SupportAppNavigator(requireActivity(), childFragmentManager, binding.container.id)
        viewModel.setNavigator(navigator)
        initComponent()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, vmFactory)[PortfolioGraphViewModel::class.java]
        viewModel.observe(this, ::onProgress, ::onError)
        viewModel.liveData.observe(this, ::onDataChanged)
    }

    private fun initComponent() {
        Log.e("initComponent", "$screenNumber")
        titles?.let {
            adapter = PortfolioGraphRVAdapter(requireContext(), it, this)
        }
        binding.rvPortfolioGraph.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPortfolioGraph.adapter = adapter
        if (childFragmentManager.findFragmentById(binding.container.id) == null) {
            viewModel.navigateToSubScreen(Screens.PROFITABILITY)
        }
        binding.btnInvest.setOnClickListener {

        }
    }

    override fun setToolbar() {
        (requireActivity() as MainActivity).initToolbar(R.string.portfolio_graph, true)
    }

    private fun onDataChanged(screenNumber: Int) {
        Log.e("onDataChanged", "$screenNumber")
        this.screenNumber = screenNumber
        replaceSubFragment()
        adapter.setCurrentScreen(screenNumber)
    }

    override fun saveSubFragment(number: Int) {
        viewModel.saveCurrentScreen(number)
    }

    private fun replaceSubFragment() {
        when (screenNumber) {
            0 -> viewModel.navigateToSubScreen(Screens.PROFITABILITY)
            1 -> viewModel.navigateToSubScreen(Screens.PAYOUTS)
            2 -> viewModel.navigateToSubScreen(Screens.PORTFOLIO_SUB)
        }
    }
}