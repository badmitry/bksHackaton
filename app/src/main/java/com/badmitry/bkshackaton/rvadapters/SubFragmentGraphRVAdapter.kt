package com.badmitry.bkshackaton.rvadapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badmitry.bkshackaton.databinding.RvSubGraphBinding
import com.badmitry.domain.entities.Finance

class SubFragmentGraphRVAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<SubFragmentGraphRVAdapter.ViewHolder>() {
    lateinit var adapterBinding: RvSubGraphBinding
    private val finances = mutableListOf<Finance>()
    private val colors = mutableListOf<Int>()

    fun setData(data: List<Finance>) {
        this.finances.clear()
        data.forEach {
            if (!colors.contains(it.colorRes)) {
                colors.add(it.colorRes)
                finances.add(it)
            }
        }
        this.finances.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvSubGraphBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(finance: Finance) {
            binding.finance = finance
            binding.ivRound.setColorFilter(context.resources.getColor(finance.colorRes))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterBinding =
            RvSubGraphBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(adapterBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(finances[position])
    }

    override fun getItemCount(): Int {
        return finances.size
    }
}