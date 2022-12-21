package uz.gita.bookapi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.bookapi.domain.usecase.*
import uz.gita.bookapi.domain.usecase.impl.*

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindSignInUseCase(impl: SignInUseCaseImpl): SignInUseCase

    @Binds
    fun bindSignInVerifyUseCase(impl: SignInVerifyUseCaseImpl): SignInVerifyUseCase

    @Binds
    fun bindSignUpUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    fun bindBaseUseCase(impl: BaseUseCaseImpl): BaseUseCase

    @Binds
    fun bindHomeUseCase(impl: HomeUseCaseImpl): HomeUseCase

}