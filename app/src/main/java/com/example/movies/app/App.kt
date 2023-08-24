package com.example.movies.app

import android.app.Application
import com.example.movies.app.di.component.AppComponent
import com.example.movies.app.di.component.DaggerAppComponent
import com.example.movies.app.di.module.AppModule
import com.example.movies.app.di.module.RetrofitModule
import com.example.movies.app.di.module.RoomModule

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .retrofitModule(RetrofitModule())
            .roomModule(RoomModule())
            .build()
    }

    companion object {
        lateinit var component: AppComponent
            private set
    }
}