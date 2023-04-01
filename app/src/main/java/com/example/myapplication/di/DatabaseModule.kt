package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.myapplication.data.source.local.db.AppDatabase
import com.example.myapplication.data.source.local.db.CharacterDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Characters.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase): CharacterDao = db.characterDao()
}