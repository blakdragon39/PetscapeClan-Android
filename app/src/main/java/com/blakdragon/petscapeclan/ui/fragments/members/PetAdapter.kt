package com.blakdragon.petscapeclan.ui.fragments.members

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.CellPetBinding
import com.blakdragon.petscapeclan.models.Pet

class PetAdapter(
    pets: List<Pet>,
    private val onPetSelected: (Pet, Boolean) -> Unit
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    private val petSelectors = pets.map { PetSelector(it, false) }

    override fun getItemCount(): Int = petSelectors.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder =
        PetViewHolder(CellPetBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) = holder.bindView(petSelectors[position], position)

    inner class PetViewHolder(private val binding: CellPetBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindView(petSelector: PetSelector, position: Int) {
            binding.tvPetName.text = petSelector.pet.type.name
            binding.tvPetName.isSelected = petSelector.isSelected

            val colorId = if (petSelector.isSelected) R.color.onPrimary else R.color.primary
            binding.tvPetName.setTextColor(ContextCompat.getColor(itemView.context, colorId))

            binding.root.setOnClickListener {
                petSelector.isSelected = !petSelector.isSelected
                notifyItemChanged(position)
                onPetSelected(petSelector.pet, petSelector.isSelected)
            }
        }
    }
}

class PetSelector(
    val pet: Pet,
    var isSelected: Boolean
)