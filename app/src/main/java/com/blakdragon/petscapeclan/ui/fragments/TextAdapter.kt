package com.blakdragon.petscapeclan.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blakdragon.petscapeclan.databinding.CellTextBinding

class TextAdapter(
    private val strings: List<String>
) : RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

    override fun getItemCount(): Int = strings.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder =
        TextViewHolder(CellTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) = holder.bindView(strings[position])

    class TextViewHolder(private val binding: CellTextBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(text: String) {
            binding.tvTitle.text = text
        }
    }
}