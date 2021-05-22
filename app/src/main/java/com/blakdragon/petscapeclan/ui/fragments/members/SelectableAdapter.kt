package com.blakdragon.petscapeclan.ui.fragments.members

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.CellSelectableTextBinding

class SelectableAdapter<T>(
    private val selectables: List<SelectableObject<T>>,
    private val onObjectSelected: (T, Boolean) -> Unit
) : RecyclerView.Adapter<SelectableAdapter<T>.PetViewHolder>() {

    override fun getItemCount(): Int = selectables.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder =
        PetViewHolder(CellSelectableTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) = holder.bindView(selectables[position], position)

    inner class PetViewHolder(private val binding: CellSelectableTextBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindView(selectable: SelectableObject<T>, position: Int) {
            binding.tvTitle.text = selectable.text
            binding.tvTitle.isSelected = selectable.isSelected

            val colorId = if (selectable.isSelected) R.color.onPrimary else R.color.primary
            binding.tvTitle.setTextColor(ContextCompat.getColor(itemView.context, colorId))

            binding.root.setOnClickListener {
                selectable.isSelected = !selectable.isSelected
                notifyItemChanged(position)
                onObjectSelected(selectable.`object`, selectable.isSelected)
            }
        }
    }
}

class SelectableObject<T>(
    val `object`: T,
    val text: String,
    var isSelected: Boolean = false
)