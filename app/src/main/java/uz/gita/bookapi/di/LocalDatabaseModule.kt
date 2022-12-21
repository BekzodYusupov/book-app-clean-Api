package uz.gita.bookapi.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.bookapi.data.source.local.AppDatabase
import uz.gita.bookapi.data.source.local.dao.BookDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {

    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "localRoom.db").allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): BookDao = appDatabase.bookDao()
}