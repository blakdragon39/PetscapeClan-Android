package com.blakdragon.petscapeclan.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import com.blakdragon.petscapeclan.R
import com.blakdragon.petscapeclan.databinding.PopupFiltersBinding

class FilterPopup {

    fun show(view: View, viewModel: HomeViewModel) {
        val binding: PopupFiltersBinding = DataBindingUtil.inflate(LayoutInflater.from(view.context), R.layout.popup_filters, null, false)
        binding.viewModel = viewModel

        PopupWindow(view.context).apply {
            binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

            contentView = binding.root
            isFocusable = true

            showAsDropDown(view)
        }
    }
}