package uz.gita.bookapi.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.SignInRequest
import uz.gita.bookapi.utils.ResultData

interface SignInUseCase {
    fun signIn(signInRequest: SignInRequest): Flow<ResultData<Unit>>
    fun checkSignInput(password: String, phone: String): Pair<Boolean, String>
}