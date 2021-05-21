package com.blakdragon.petscapeclan.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.databinding.BindingAdapter
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.ViewRankBinding
import com.blakdragon.petscapeclan.models.enums.Rank

class RankView(c: Context, attrs: AttributeSet): FrameLayout(c, attrs) {

    val binding = ViewRankBinding.inflate(LayoutInflater.from(c), this, true)

    var rank: Rank? = null

    init {
        c.obtainStyledAttributes(attrs, R.styleable.RankView).use { styles ->
            binding.tvRank.setTextColor(styles.getColor(
                R.styleable.RankView_textColor,
                ContextCompat.getColor(c, R.color.onPrimary)
            ))
        }
    }
}

@BindingAdapter("rank")
fun setRank(view: RankView, rank: Rank) {
    view.binding.ivRank.setImageResource(rank.iconId)
    view.binding.tvRank.setText(rank.textId)

    view.rank = rank
}