package uz.gita.bookapi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.SignInRequest
import uz.gita.bookapi.domain.repository.AuthRepository
import uz.gita.bookapi.domain.usecase.BaseUseCase
import uz.gita.bookapi.domain.usecase.SignInUseCase
import uz.gita.bookapi.utils.ResultData
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val baseUseCase: BaseUseCase
) : SignInUseCase {
    override fun signIn(signInRequest: SignInRequest): Flow<ResultData<Unit>> {
        return authRepository.signIn(signInRequest)
    }

    override fun checkSignInput(password: String, phone: String): Pair<Boolean, String> {
        val reformatPhone = baseUseCase.reformatPhone(phone)
        return Pair(password.length >= 6 && reformatPhone.length == 13, reformatPhone)
    }
}

