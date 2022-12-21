package uz.gita.bookapi.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.bookapi.data.source.remote.dto.request.VerifyRequest
import uz.gita.bookapi.domain.usecase.SignInVerifyUseCase
import uz.gita.bookapi.navigation.Navigator
import uz.gita.bookapi.presentation.screen.signInVerify.SignInVerifyScreenDirections
import uz.gita.bookapi.presentation.screen.signInVerify.SignInVerifyViewModel
import uz.gita.bookapi.utils.LoadingType
import uz.gita.bookapi.utils.ResultData
import javax.inject.Inject

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/15
Time: 12:11
 */

@HiltViewModel
class SignInVerifyViewModelImpl @Inject constructor(
    private val useCase: SignInVerifyUseCase,
    private val navigator: Navigator
) : SignInVerifyViewModel, ViewModel() {

    override val failureFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val successFlow: MutableSharedFlow<Any> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val loading: MutableSharedFlow<LoadingType> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val hasConnection: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val isValidFlow: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)


    override fun openHomeScreen() {
        viewModelScope.launch {
            navigator.navigateTo(SignInVerifyScreenDirections.actionSignInVerifyScreenToBaseScreen())
        }
    }

    override fun signInVerify(verifyRequest: VerifyRequest) {
        viewModelScope.launch {
            useCase.signUpVerify(verifyRequest).collect { resultData ->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(Unit)
                }
            }
        }
    }

}