package uz.gita.bookapi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.VerifyRequest
import uz.gita.bookapi.domain.repository.AuthRepository
import uz.gita.bookapi.domain.usecase.SignInVerifyUseCase
import uz.gita.bookapi.utils.ResultData
import javax.inject.Inject

class SignInVerifyUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
): SignInVerifyUseCase {
    override fun signUpVerify(verifyRequest: VerifyRequest): Flow<ResultData<Unit>> =
        authRepository.signInVerify(verifyRequest)
}