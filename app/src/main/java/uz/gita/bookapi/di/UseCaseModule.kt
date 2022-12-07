package uz.gita.bookapi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.bookapi.domain.usecase.BaseUseCase
import uz.gita.bookapi.domain.usecase.SignInUseCase
import uz.gita.bookapi.domain.usecase.SignUpUseCase
import uz.gita.bookapi.domain.usecase.impl.BaseUseCaseImpl
import uz.gita.bookapi.domain.usecase.impl.SignInUseCaseImpl
import uz.gita.bookapi.domain.usecase.impl.SignUpUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindSignInUseCase(impl: SignInUseCaseImpl): SignInUseCase

    @Binds
    fun bindSignUpUseCase(impl: SignUpUseCaseImpl):SignUpUseCase

    @Binds
    fun bindBaseUseCase(impl: BaseUseCaseImpl):BaseUseCase
}