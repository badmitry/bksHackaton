package com.badmitry.bkshackaton.rvadapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badmitry.bkshackaton.R
import com.badmitry.bkshackaton.databinding.RvOptionsCurrencyBinding
import com.badmitry.bkshackaton.view.SetCurrencyView

class FragmentOptionsCurrencyRVAdapter(
    private val fragment: SetCurrencyView,
    private val context: Context
) :
    RecyclerView.Adapter<FragmentOptionsCurrencyRVAdapter.ViewHolder>() {
    lateinit var adapterBinding: RvOptionsCurrencyBinding
    private val currencyList = context.resources.getStringArray(R.array.currency)
    private val currencyValuesList = context.resources.getStringArray(R.array.currency_value)

    inner class ViewHolder(private val binding: RvOptionsCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(currency: String, value: String) {
            binding.tvCurrency.text = currency
            binding.tvValue.text = value
            binding.llCurrency.setOnClickListener {
                fragment.setCurrency(currency, value)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterBinding =
            RvOptionsCurrencyBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(adapterBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currencyList[position], currencyValuesList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }
}