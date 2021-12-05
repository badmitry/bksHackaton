package com.badmitry.bkshackaton.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.badmitry.bkshackaton.MainActivity
import com.badmitry.bkshackaton.R
import com.badmitry.bkshackaton.SaverParams
import com.badmitry.bkshackaton.databinding.FragmentPortfolioBinding
import com.badmitry.bkshackaton.rvadapters.PortfolioRVAdapter


class FragmentPortfolio : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var adapter: PortfolioRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        initComponent()
    }

    private fun initComponent() {
        binding.layoutProfitability.cvProfitability.visibility = View.VISIBLE
        SaverParams.instance.paramsOfPortfolio?.let {
            binding.layoutProfitability.params = it
            adapter = PortfolioRVAdapter(requireContext(), it.bonds)
            binding.rvPortfolio.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPortfolio.adapter = adapter
        }
    }

    private fun setToolbar() {
        (requireActivity() as MainActivity).initToolbar(R.string.portfolio, true)
    }
}