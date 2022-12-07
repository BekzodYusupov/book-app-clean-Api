package uz.gita.bookapi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.SignUpRequest
import uz.gita.bookapi.domain.repository.AuthRepository
import uz.gita.bookapi.domain.usecase.BaseUseCase
import uz.gita.bookapi.domain.usecase.SignUpUseCase
import uz.gita.bookapi.utils.ResultData
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val baseUseCase: BaseUseCase
) : SignUpUseCase {
    override fun signUp(signUpRequest: SignUpRequest): Flow<ResultData<Unit>> {
        return authRepository.signUp(signUpRequest)
    }

    override fun isValidInput(signUpRequest: SignUpRequest, rePassword: String): Pair<Boolean, SignUpRequest> {
        val reformatPhone = baseUseCase.reformatPhone(signUpRequest.phone)
        if (rePassword != signUpRequest.password) return Pair(false, signUpRequest)
        signUpRequest.phone = reformatPhone
        val isValid = reformatPhone.length == 13 && signUpRequest.password.length >= 6 &&
                signUpRequest.lastName.length >= 3 && signUpRequest.firstName.length >= 3
        return Pair(isValid, signUpRequest)
    }
}