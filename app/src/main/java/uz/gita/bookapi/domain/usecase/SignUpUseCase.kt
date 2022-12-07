package uz.gita.bookapi.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.SignUpRequest
import uz.gita.bookapi.utils.ResultData

/**
Created: Bekzod Yusupov
Time: 17:55
 */
interface SignUpUseCase {
    fun signUp(signUpRequest: SignUpRequest): Flow<ResultData<Unit>>
    fun isValidInput(signUpRequest: SignUpRequest, rePassword: String): Pair<Boolean, SignUpRequest>
}