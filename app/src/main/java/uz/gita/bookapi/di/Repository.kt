package uz.gita.bookapi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookapi.data.repositoryImpl.AuthRepositoryImpl
import uz.gita.bookapi.data.repositoryImpl.BookRepositoryImpl
import uz.gita.bookapi.domain.repository.AuthRepository
import uz.gita.bookapi.domain.repository.BookRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface Repository {

    @Binds
    @Singleton
    fun authRepositoryBind(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bookRepositoryBind(impl: BookRepositoryImpl): BookRepository
}