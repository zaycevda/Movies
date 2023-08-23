package com.example.movies.app.di.utils

import androidx.fragment.app.Fragment
import com.example.movies.app.di.component.DiComponent
import com.example.movies.app.di.holder.FeatureComponentHolder
import com.example.movies.app.di.holder.FragmentComponentHolderProperty
import kotlin.properties.ReadOnlyProperty

fun <Holder : FeatureComponentHolder<Component>, Component : DiComponent> Fragment.featureComponent(
    holder: Holder
): ReadOnlyProperty<Fragment, Component> {
    return FragmentComponentHolderProperty(holder)
}