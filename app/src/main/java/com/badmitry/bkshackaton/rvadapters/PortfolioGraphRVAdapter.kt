package com.badmitry.bkshackaton.rvadapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badmitry.bkshackaton.databinding.RvPortfolioGraphBinding
import com.badmitry.bkshackaton.view.SubFragmentSaver

class PortfolioGraphRVAdapter(
    private val context: Context,
    private val titles: Array<out String>,
    private val fragment: SubFragmentSaver
) :
    RecyclerView.Adapter<PortfolioGraphRVAdapter.ViewHolder>() {
    lateinit var adapterBinding: RvPortfolioGraphBinding
    private var checkedItem = 0

    fun setCurrentScreen(currentScreen: Int) {
        checkedItem = currentScreen
        notifyDataSetChanged()
    }
//    fun setData(data: List<ExchangeRates>) {
//        this.data.clear()
//        this.data.addAll(data)
//        notifyDataSetChanged()
//    }

    inner class ViewHolder(private val binding: RvPortfolioGraphBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(title: String, position: Int) {
            binding.title.text = title
            binding.cvLayout.setOnClickListener {
                checkedItem = position
                fragment.saveSubFragment(position)
                notifyDataSetChanged()
            }
            if (position == checkedItem) {
                binding.title.setTextColor(Color.BLUE)
            } else {
                binding.title.setTextColor(Color.BLACK)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterBinding =
            RvPortfolioGraphBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(adapterBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(titles[position], position)
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}