package uz.gita.bookapi.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {
//    @Provides
//    @Singleton
//    fun provideShP(@ApplicationContext context: Context): SharedPref {
//        Pluto.initialize(context)
//        return SharedPref(context)
//    }
}