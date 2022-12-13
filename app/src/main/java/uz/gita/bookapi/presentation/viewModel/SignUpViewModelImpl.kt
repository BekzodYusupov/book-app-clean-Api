package uz.gita.bookapi.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.bookapi.data.source.remote.dto.request.SignUpRequest
import uz.gita.bookapi.domain.usecase.SignUpUseCase
import uz.gita.bookapi.navigation.Navigator
import uz.gita.bookapi.presentation.screen.signUp.SignUpScreenDirections
import uz.gita.bookapi.presentation.screen.signUp.SignUpViewModel
import uz.gita.bookapi.utils.LoadingType
import uz.gita.bookapi.utils.ResultData
import uz.gita.bookapi.utils.mLog
import javax.inject.Inject

@HiltViewModel
class SignUpViewModelImpl @Inject constructor(
    private val useCase: SignUpUseCase,
    private val navigator: Navigator
) : SignUpViewModel, ViewModel() {

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

    override fun signUp(signUpRequest: SignUpRequest, rePassword: String) {
        viewModelScope.launch {
            val isValidWithData = useCase.isValidInput(signUpRequest, rePassword)
            mLog("isValid result from useCase = ${isValidWithData.first}")
            mLog("result data from useCase = ${isValidWithData.second}")
            isValidFlow.emit(isValidWithData.first)
            if (isValidWithData.first) {
                mLog("validInput from signUp")
                useCase.signUp(isValidWithData.second).collectLatest { resultData ->
                    when (resultData) {
                        is ResultData.Failure -> {
                            failureFlow.emit(resultData.message)
                        }
                        is ResultData.HasConnection -> {
                            hasConnection.emit(resultData.state)
                        }
                        is ResultData.Loading -> {
                            loading.emit(resultData.state)
                        }
                        is ResultData.Success -> {
                            successFlow.emit(Unit)
                        }
                    }
                }
            }
        }
    }

    override fun openSignIn() {
        viewModelScope.launch {
            navigator.navigateTo(SignUpScreenDirections.actionSignUpScreenToSignInScreen())
        }
    }

    override fun openVerify() {
        viewModelScope.launch {
            navigator.navigateTo(SignUpScreenDirections.actionSignUpScreenToSignUpVerifyScreen())
        }
    }
}