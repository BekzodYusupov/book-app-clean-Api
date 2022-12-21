package uz.gita.bookapi.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.VerifyRequest
import uz.gita.bookapi.utils.ResultData

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/15
Time: 12:05
 */
interface SignInVerifyUseCase {
    fun signUpVerify(verifyRequest: VerifyRequest): Flow<ResultData<Unit>>
}