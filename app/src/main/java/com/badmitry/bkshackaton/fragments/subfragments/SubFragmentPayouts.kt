package com.badmitry.bkshackaton.fragments.subfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.badmitry.bkshackaton.R
import com.badmitry.bkshackaton.databinding.SubFragmentPayoutsBinding
import com.badmitry.bkshackaton.fragments.BaseFragment
import com.badmitry.bkshackaton.rvadapters.SubFragmentGraphRVAdapter
import com.badmitry.bkshackaton.viewmodels.SubFragmentPayoutsViewModel
import com.badmitry.castomviews.PayoutsGraph
import com.badmitry.domain.entities.Finance
import javax.inject.Inject


class SubFragmentPayouts : BaseFragment() {
    private lateinit var binding: SubFragmentPayoutsBinding
    private lateinit var viewModel: SubFragmentPayoutsViewModel
    private lateinit var adapter: SubFragmentGraphRVAdapter
    private var payoutsGraph: PayoutsGraph? = null
    private val finances = mutableListOf<Finance>()

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
//        viewModel.saveCurrentScreen(finances)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SubFragmentPayoutsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        finances.clear()
        finances.add(Finance(R.color.bks_blue, 0, getString(R.string.bonds), 0))
        finances.add(Finance(R.color.bks_green, 0, getString(R.string.cash_bonds), 0))
        finances.add(Finance(R.color.bks_orange, 0, getString(R.string.ndfl), 0))
        viewModel.getFinance(finances)
    }

    private fun initComponent() {
        adapter = SubFragmentGraphRVAdapter(requireContext())
        binding.rvBonds.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBonds.adapter = adapter
        if (payoutsGraph == null) {
            payoutsGraph = PayoutsGraph(requireContext())
            binding.payoutsGraphLayout.addView(payoutsGraph)
            binding.payoutsGraphLayout.setOnClickListener {  }
        }
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, vmFactory)[SubFragmentPayoutsViewModel::class.java]
        viewModel.observe(this, ::onProgress, ::onError)
        viewModel.liveData.observe(this, ::onDataChanged)
    }

    override fun setToolbar() {

    }

    private fun onDataChanged(finances: List<Finance>) {
        adapter.setData(finances)
        payoutsGraph?.setFinances(finances)
    }
}