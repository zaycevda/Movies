package com.example.movies.app.di.component

import com.example.movies.app.di.module.AppModule
import com.example.movies.app.di.module.RetrofitModule
import com.example.movies.app.di.module.RoomModule
import com.example.movies.data.db.db.RoomDb
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RetrofitModule::class,
        RoomModule::class
    ]
)
interface AppComponent {
    fun retrofit(): Retrofit
    fun room(): RoomDb
}