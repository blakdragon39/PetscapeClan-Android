package com.blakdragon.petscapeoffline.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment<T> : Fragment() {

    val parentActivity: T
        get() = requireActivity() as T
}