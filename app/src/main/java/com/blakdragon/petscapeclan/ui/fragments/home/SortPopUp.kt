package com.blakdragon.petscapeclan.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.PopupSortingBinding

class SortPopUp {

    fun show(view: View, viewModel: HomeViewModel) {
        val binding: PopupSortingBinding = DataBindingUtil.inflate(LayoutInflater.from(view.context), R.layout.popup_sorting, null, false)
        binding.viewModel = viewModel

        binding.rbDefault.setOnCheckedChangeListener { _, isChecked -> if (isChecked) viewModel.sortMethod.value = HomeSortMethod.DEFAULT }
        binding.rbAlphabetical.setOnCheckedChangeListener { _, isChecked -> if (isChecked) viewModel.sortMethod.value = HomeSortMethod.ALPHABETICAL }

        PopupWindow(view.context).apply {
            binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

            contentView = binding.root
            isFocusable = true

            showAsDropDown(view)
        }
    }
}