package com.example.collegealert.di

import android.content.Context
import com.example.collegealert.Db.EventsDAO
import com.example.collegealert.Db.EventsDB
import com.example.collegealert.alarm.AlarmManager
import com.example.collegealert.repos.EventsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideEventRepo(eventDAO: EventsDAO):EventsRepo = EventsRepo(eventDAO)

    @Provides
    fun provideEventDAO(@ApplicationContext context: Context): EventsDAO{
        return EventsDB.getInstance(context).dao()
    }
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context):AlarmManager = AlarmManager(context)


}