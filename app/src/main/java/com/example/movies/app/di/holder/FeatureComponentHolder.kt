package com.example.movies.app.di.holder

import com.example.movies.app.di.component.DiComponent
import java.lang.ref.WeakReference

abstract class FeatureComponentHolder<Component: DiComponent> {

    private var component: WeakReference<Component>? = null

    fun get() = component?.get() ?: build().also { set(it) }

    fun set(component: Component) {
        this.component = WeakReference(component)
    }

    fun clear() {
        component = null
    }

    abstract fun build(): Component
}