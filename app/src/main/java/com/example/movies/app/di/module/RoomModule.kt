package com.example.movies.app.di.module

import android.content.Context
import androidx.room.Room
import com.example.movies.data.db.db.RoomDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideRoom(context: Context) =
        Room.databaseBuilder(
            context = context,
            klass = RoomDb::class.java,
            name = "room_database"
        ).build()
}