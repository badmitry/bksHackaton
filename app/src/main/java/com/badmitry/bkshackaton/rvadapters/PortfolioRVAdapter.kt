package com.badmitry.bkshackaton.rvadapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badmitry.bkshackaton.databinding.RvPortfolioBinding
import com.badmitry.domain.entities.BondData

class PortfolioRVAdapter(
    private val context: Context,
    private val bonds: List<BondData>
) :
    RecyclerView.Adapter<PortfolioRVAdapter.ViewHolder>() {
    lateinit var adapterBinding: RvPortfolioBinding

    fun notify(currentScreen: Int) {
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvPortfolioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(bond: BondData) {
            Log.e("bind", "${bond.name}")
            binding.bond = bond
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterBinding =
            RvPortfolioBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(adapterBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bonds[position])
    }

    override fun getItemCount(): Int {
        return bonds.size
    }
}