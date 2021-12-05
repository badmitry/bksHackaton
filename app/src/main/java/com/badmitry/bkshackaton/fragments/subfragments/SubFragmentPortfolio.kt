package com.badmitry.bkshackaton.fragments.subfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.badmitry.bkshackaton.R
import com.badmitry.bkshackaton.databinding.SubFragmentPortfolioBinding
import com.badmitry.bkshackaton.fragments.BaseFragment
import com.badmitry.bkshackaton.rvadapters.SubFragmentPortfolioRVAdapter
import com.badmitry.bkshackaton.viewmodels.SubFragmentPortfolioViewModel
import com.badmitry.castomviews.PortfolioGraph
import com.badmitry.domain.entities.Bond
import javax.inject.Inject


class SubFragmentPortfolio : BaseFragment() {
    private lateinit var binding: SubFragmentPortfolioBinding
    private lateinit var viewModel: SubFragmentPortfolioViewModel
    private var portfolioGraph: PortfolioGraph? = null
    private lateinit var adapter: SubFragmentPortfolioRVAdapter
//    private val bonds = mutableListOf(
//        Bond(R.color.bks_blue, 60, "fierst", "fiers"),
//        Bond(R.color.bks_black, 35, "second", "second"),
//        Bond(R.color.bks_light_blue, 4, "third", "third"),
//        Bond(R.color.bks_grey, 1, "fourth", "fourth")
//    )

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
        binding = SubFragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        val listBonds = mutableListOf<Bond>(
            Bond(
                R.color.bks_green,
                title = getString(R.string.gos_bonds),
                name = getString(R.string.gos_bonds)
            ),
            Bond(
                R.color.bks_orange,
                title = getString(R.string.corp_bonds),
                name = getString(R.string.corp_bonds)
            ),
            Bond(R.color.bks_grey,
                title = getString(R.string.cash_bonds),
                name = getString(R.string.cash_bonds))
        )
        viewModel.getBonds(listBonds)
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, vmFactory)[SubFragmentPortfolioViewModel::class.java]
        viewModel.observe(this, ::onProgress, ::onError)
        viewModel.liveData.observe(this, ::onDataChanged)
    }

    private fun initComponent() {
        adapter = SubFragmentPortfolioRVAdapter(requireContext())
        binding.rvBonds.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBonds.adapter = adapter
        if (portfolioGraph == null) {
            portfolioGraph = PortfolioGraph(requireContext())
            binding.portfolioGraphLayout.addView(portfolioGraph)
        }
    }

    override fun setToolbar() {

    }

    private fun onDataChanged(list: List<Bond>) {
        portfolioGraph?.setBonds(list)
        adapter.setData(list)
    }

//    companion object {
//        fun createInstance(list: List<Bond>) {
//            SubFragmentPortfolio().apply {
//                arguments = Bundle().apply {
//                    bonds.addAll(list)
//                }
//            }
//        }
//    }
}