package com.example.movies.app.viewmodel.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movies.app.viewmodel.Factory

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline create: (stateHandle: SavedStateHandle) -> T
) = viewModels<T> {
    Factory(this, create)
}