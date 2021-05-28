package com.blakdragon.petscapeclan.ui.fragments.members

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blakdragon.petscapeclan.databinding.CellAltAddBinding
import com.blakdragon.petscapeclan.databinding.CellAltNameBinding
import java.lang.IllegalStateException

class AltsAdapter(
    private val onAddNameClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_NAME = 1
        private const val VIEW_TYPE_ADD = 2
    }

    private var items: List<AltAdapterItem> = listOf()

    fun setNames(names: List<String>) {
        val newItems: MutableList<AltAdapterItem> = mutableListOf()

        newItems.addAll(names.map { NameItem(it) })
        newItems.add(AddItem())

        val result = DiffUtil.calculateDiff(AltsAdapterDiffUtil(items, newItems))
        items = newItems
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is NameItem -> VIEW_TYPE_NAME
            is AddItem -> VIEW_TYPE_ADD
            else -> throw IllegalStateException("getItemViewType not set up correctly")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NAME -> NameViewHolder(CellAltNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            VIEW_TYPE_ADD -> AddViewHolder(CellAltAddBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalStateException("getItemViewType not set up correctly")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NameViewHolder -> holder.bindView(items[position] as NameItem)
            is AddViewHolder -> holder.bindView()
        }
    }

    inner class NameViewHolder(private val binding: CellAltNameBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: NameItem) {
            binding.tvName.text = item.name
            //todo delete a name
        }
    }

    inner class AddViewHolder(private val binding: CellAltAddBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView() {
            binding.root.setOnClickListener { onAddNameClick() }
        }
    }
}

abstract class AltAdapterItem

class NameItem(val name: String) : AltAdapterItem()

class AddItem : AltAdapterItem()

class AltsAdapterDiffUtil(
    val oldItems: List<AltAdapterItem>,
    val newItems: List<AltAdapterItem>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition]::class == newItems[newItemPosition]::class
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldItems[oldItemPosition] as? NameItem)?.name == (newItems[newItemPosition] as? NameItem)?.name
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size
}