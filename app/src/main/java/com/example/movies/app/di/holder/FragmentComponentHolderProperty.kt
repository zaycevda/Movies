package com.example.movies.app.di.holder

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.movies.app.di.component.DiComponent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentComponentHolderProperty<Component : DiComponent, Holder : FeatureComponentHolder<Component>>(
    private val holder: Holder
) : ReadOnlyProperty<Fragment, Component> {

    private val lifecycleObserver = ComponentHolderLifecycleObserver()

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Component {
        thisRef.viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        return holder.get()
    }

    private inner class ComponentHolderLifecycleObserver : DefaultLifecycleObserver {
        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            holder.clear()
        }
    }
}