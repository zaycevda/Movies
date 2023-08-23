package com.example.movies.app.di.component

import com.example.movies.app.di.module.AppModule
import com.example.movies.app.di.module.RetrofitModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RetrofitModule::class
    ]
)
interface AppComponent {
    fun retrofit(): Retrofit
}