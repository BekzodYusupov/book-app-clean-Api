package uz.gita.bookapi.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.SignInRequest
import uz.gita.bookapi.data.source.remote.dto.request.SignUpRequest
import uz.gita.bookapi.data.source.remote.dto.request.VerifyRequest
import uz.gita.bookapi.utils.ResultData

interface AuthRepository {
    fun signUp(signUpRequest: SignUpRequest): Flow<ResultData<Unit>>
    fun signUpVerify(verifyRequest: VerifyRequest): Flow<ResultData<Unit>>
    fun signIn(signInRequest: SignInRequest): Flow<ResultData<Unit>>
    fun signInVerify(verifyRequest: VerifyRequest): Flow<ResultData<Unit>>
}