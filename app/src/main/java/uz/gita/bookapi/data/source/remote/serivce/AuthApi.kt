package uz.gita.bookapi.data.source.remote.serivce

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.bookapi.data.source.remote.dto.request.SignInRequest
import uz.gita.bookapi.data.source.remote.dto.request.SignUpRequest
import uz.gita.bookapi.data.source.remote.dto.request.VerifyRequest
import uz.gita.bookapi.data.source.remote.dto.response.TokenResponse

interface AuthApi {
    @POST("auth/sign-up")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<TokenResponse>

    @POST("auth/sign-up/verify")
    suspend fun signUpVerify(
        @Header("Authorization") bearerToken: String,
        @Body verifyRequest: VerifyRequest
    ): Response<TokenResponse>

    @POST("auth/sign-in")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): Response<TokenResponse>

    @POST("auth/sign-in/verify")
    suspend fun signInVerify(
        @Header("Authorization") bearerToken: String,
        @Body verifyRequest: VerifyRequest
    ): Response<TokenResponse>
}