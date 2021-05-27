package com.blakdragon.petscapeclan.ui.fragments.members

import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import com.blakdragon.petscapeclan.databinding.PopupRankBinding
import com.blakdragon.petscapeclan.models.enums.Rank

class RankPopup {

    private var popup: PopupWindow? = null

    fun show(view: View, onRankClick: (Rank) -> Unit) {
        val binding = PopupRankBinding.inflate(LayoutInflater.from(view.context))

        PopupWindow(view.context).apply {
            binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

            contentView = binding.root
            isFocusable = true

            binding.rankBronze.setOnClickListener { onRankClick(Rank.Bronze) }
            binding.rankIron.setOnClickListener { onRankClick(Rank.Iron) }
            binding.rankSteel.setOnClickListener { onRankClick(Rank.Steel) }
            binding.rankGold.setOnClickListener { onRankClick(Rank.Gold) }
            binding.rankMithril.setOnClickListener { onRankClick(Rank.Mithril) }
            binding.rankAdamant.setOnClickListener { onRankClick(Rank.Adamant) }
            binding.rankRune.setOnClickListener { onRankClick(Rank.Rune) }
            binding.rankDragon.setOnClickListener { onRankClick(Rank.Dragon) }
            binding.rankAdmin.setOnClickListener { onRankClick(Rank.Admin) }
            binding.rankDeputyOwner.setOnClickListener { onRankClick(Rank.DeputyOwner) }
            binding.rankOwner.setOnClickListener { onRankClick(Rank.Owner) }

            showAsDropDown(view)

            popup = this
        }
    }

    fun dismiss() = popup?.dismiss()
}