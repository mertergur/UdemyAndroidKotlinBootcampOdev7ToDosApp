package com.example.todosapp.di

import android.content.Context
import androidx.room.Room
import com.example.todosapp.data.datasource.ToDosDataSource
import com.example.todosapp.data.repo.ToDosRepository
import com.example.todosapp.room.Database
import com.example.todosapp.room.ToDosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideToDosDao(@ApplicationContext context: Context): ToDosDao{
        val db = Room.databaseBuilder(context, Database::class.java, "todos.sqlite")
            .createFromAsset("todos.sqlite").build()

        return db.getToDosDao()
    }

    @Provides
    @Singleton
    fun provideToDosDataSource(tdDao: ToDosDao): ToDosDataSource{
        return ToDosDataSource(tdDao)
    }

    @Provides
    @Singleton
    fun provideToDosRepository(tdds: ToDosDataSource): ToDosRepository{
        return ToDosRepository(tdds)
    }
}