package uz.gita.bookapi.data.repositoryImpl

import android.util.Log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bookapi.data.source.local.shared.SharedPref
import uz.gita.bookapi.data.source.remote.dto.request.SignInRequest
import uz.gita.bookapi.data.source.remote.dto.request.SignUpRequest
import uz.gita.bookapi.data.source.remote.dto.request.VerifyRequest
import uz.gita.bookapi.data.source.remote.serivce.AuthApi
import uz.gita.bookapi.domain.repository.AuthRepository
import uz.gita.bookapi.utils.ConnectionUtil
import uz.gita.bookapi.utils.LoadingType
import uz.gita.bookapi.utils.ResultData
import uz.gita.bookapi.utils.mLog
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPref,
    private val authApi: AuthApi,
    private val connectionUtil: ConnectionUtil
) : AuthRepository {
    override fun signUp(signUpRequest: SignUpRequest): Flow<ResultData<Unit>> = flow<ResultData<Unit>> {
        emit(ResultData.HasConnection(true))

        if (connectionUtil.hasConnection()) {
                mLog("repo-signUp-hasConnection")
                emit(ResultData.Loading(LoadingType(fullScreen = false)))
                val response = authApi.signUp(signUpRequest)
                mLog("repo-signUp-hasConnection after response")
                if (response.isSuccessful) {
                    mLog("repo-signUp-hasConnection after response is successfully managed")
                    response.body()?.let { token ->
                        mLog("repo-signUp-hasConnection after response is successfully managed")
//                        sharedPref.token = token.token
                        sharedPref.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo2NCwiaXNzIjoiaHR0cDovLzEyNy4wLjAuMTo4MDgyLyIsImV4cCI6MTY5NjAwNDE5Mn0.t-4xv6o7LxfHGYuLsxOyfYzlvwLyCyGqPpaT6qf5LSU"
                        emit(ResultData.Success(Unit))
                        emit(ResultData.Loading(LoadingType(fullScreen = true)))
                    }
                } else {
                    response.body()?.let {
                        emit(ResultData.Failure(it.token))
                        emit(ResultData.Loading(LoadingType(fullScreen = false)))
                    }
                }
            } else {
                emit(ResultData.HasConnection(false))
            }
        }.catch { error ->
            error.message?.let {
                mLog("Failure ${error.cause}")
                mLog("message ${error.message}")
                mLog("Failure ${error.cause}")
                emit(ResultData.Failure(it))
                emit(ResultData.Loading(LoadingType(fullScreen = false)))
            }
        }.flowOn(IO)

    override fun signUpVerify(verifyRequest: VerifyRequest): Flow<ResultData<Unit>> =
        flow<ResultData<Unit>> {
            if (connectionUtil.hasConnection()) {
                emit(ResultData.HasConnection(true))
                emit(ResultData.Loading(LoadingType(fullScreen = false)))
                val response = authApi.signUpVerify("Bearer ${sharedPref.token}", verifyRequest)

                if (response.isSuccessful) {
                    response.body()?.let { token ->
                        sharedPref.token = token.token
                        emit(ResultData.Success(Unit))
                        emit(ResultData.Loading(LoadingType(fullScreen = false)))
                    }
                } else {
                    response.body()?.let {
                        emit(ResultData.Failure(it.token))
                        emit(ResultData.Loading(LoadingType(fullScreen = false)))
                    }
                }
            } else {
                emit(ResultData.HasConnection(false))
            }
        }.catch { error ->
            error.message?.let {
                emit(ResultData.Failure(it))
                emit(ResultData.Loading(LoadingType(fullScreen = false)))
            }
        }.flowOn(IO)

    override fun signIn(signInRequest: SignInRequest): Flow<ResultData<Unit>> =
        flow<ResultData<Unit>> {
            Log.d("zzz", "Repository: sign in inside method")
            if (connectionUtil.hasConnection()) {
                emit(ResultData.HasConnection(true))
                emit(ResultData.Loading(LoadingType(fullScreen = false)))
                val response = authApi.signIn(signInRequest)
                Log.d("zzz", "Repository: sign in inside if -> has Connection")
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("zzz", "Repository: sign in inside if -> has Connection->is successfull -> body-> $it")
                        emit(ResultData.Success(Unit))
                        emit(ResultData.Loading(LoadingType(fullScreen = false)))
                    }
                } else {
                    emit(ResultData.Failure(authApi.signIn(signInRequest).message()))
                }
            } else {
                Log.d("zzz", "Repository: sign in inside if else false")
                emit(ResultData.HasConnection(false))
            }
        }.catch { error ->
            error.message?.let {
                Log.d("zzz", "Repository: sign in catch message = $it")
                emit(ResultData.Failure(it))
                emit(ResultData.Loading(LoadingType(fullScreen = false)))
            }
            Log.d("zzz", "Repository: sign in catch cause = ${error.cause}")
        }.flowOn(IO)

    override fun signInVerify(verifyRequest: VerifyRequest): Flow<ResultData<Unit>> =
        flow<ResultData<Unit>> {
            if (connectionUtil.hasConnection()) {
                emit(ResultData.HasConnection(true))
                emit(ResultData.Loading(LoadingType(fullScreen = false)))
                val response = authApi.signInVerify("Bearer ${sharedPref.token}", verifyRequest)
                if (response.isSuccessful) {
                    response.body()?.let { token ->
                        sharedPref.token = token.token
                        emit(ResultData.Success(Unit))
                    }
                }
            } else emit(ResultData.HasConnection(false))
        }.catch { error ->
            error.message?.let {
                emit(ResultData.Failure(it))
                emit(ResultData.Loading(LoadingType(fullScreen = false)))
            }
        }.flowOn(IO)
}