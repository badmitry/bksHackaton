package com.badmitry.bkshackaton.rvadapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badmitry.bkshackaton.databinding.RvSubBondsBinding
import com.badmitry.domain.entities.Bond

class SubFragmentPortfolioRVAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<SubFragmentPortfolioRVAdapter.ViewHolder>() {
    lateinit var adapterBinding: RvSubBondsBinding
    private val bonds = mutableListOf<Bond>()

    fun setData(data: List<Bond>) {
        this.bonds.clear()
        this.bonds.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvSubBondsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(bond: Bond) {
            binding.bond = bond
            binding.ivRound.setColorFilter(context.resources.getColor(bond.colorRes))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterBinding =
            RvSubBondsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(adapterBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bonds[position])
    }

    override fun getItemCount(): Int {
        return bonds.size
    }
}