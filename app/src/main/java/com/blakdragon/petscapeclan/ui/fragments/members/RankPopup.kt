package com.blakdragon.petscapeclan.ui.fragments.members

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.CellRankBinding
import com.blakdragon.petscapeclan.databinding.PopupRankBinding
import com.blakdragon.petscapeclan.models.Rank
import com.blakdragon.petscapeclan.models.enums.RankType

class RankPopup {

    private var popup: PopupWindow? = null

    fun show(view: View, ranks: List<Rank>, onRankClick: (RankType) -> Unit) {
        val binding = PopupRankBinding.inflate(LayoutInflater.from(view.context))

        PopupWindow(view.context).apply {
            binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

            contentView = binding.root
            isFocusable = true

            binding.rvRanks.layoutManager = LinearLayoutManager(view.context)
            binding.rvRanks.adapter = RankAdapter(ranks, onRankClick)

            showAsDropDown(view)

            popup = this
        }
    }

    fun dismiss() = popup?.dismiss()
}

class RankAdapter(
    private val ranks: List<Rank>,
    private val onRankClick: (RankType) -> Unit
) : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    override fun getItemCount(): Int = ranks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder =
        RankViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.cell_rank, parent, false))

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) = holder.bindView(ranks[position])

    inner class RankViewHolder(val binding: CellRankBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(rank: Rank) {
            binding.rank = rank
            binding.root.setOnClickListener { onRankClick(rank.type) }
        }
    }
}