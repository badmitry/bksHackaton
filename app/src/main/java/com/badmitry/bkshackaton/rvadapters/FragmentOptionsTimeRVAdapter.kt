package com.badmitry.bkshackaton.rvadapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badmitry.bkshackaton.R
import com.badmitry.bkshackaton.databinding.RvOptionsTimeBinding
import com.badmitry.bkshackaton.view.SetTimeView

class FragmentOptionsTimeRVAdapter(
    private val fragment: SetTimeView,
    private val context: Context
) :
    RecyclerView.Adapter<FragmentOptionsTimeRVAdapter.ViewHolder>() {
    lateinit var adapterBinding: RvOptionsTimeBinding
    private val timeList = mutableListOf<Int>()

    init {
        for (i in 1..20) {
            timeList.add(i)
        }
    }

    inner class ViewHolder(private val binding: RvOptionsTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(time: Int, position: Int) {
            var year = ""
            if (position == 0) {
                year = context.getString(R.string.year)
            } else if (position in 1..3) {
                year = context.getString(R.string.years)
            } else {
                year = context.getString(R.string.many_years)
            }
            binding.tvYear.text = year
            binding.tvTime.text = time.toString()
            binding.llTime.setOnClickListener {
                fragment.setTime(time.toString(), year)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        adapterBinding =
            RvOptionsTimeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(adapterBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(timeList[position], position)
    }

    override fun getItemCount(): Int {
        return timeList.size
    }
}