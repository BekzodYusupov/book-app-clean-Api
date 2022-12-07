package uz.gita.bookapi.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.bookapi.data.source.remote.dto.request.SignInRequest
import uz.gita.bookapi.domain.usecase.SignInUseCase
import uz.gita.bookapi.navigation.Navigator
import uz.gita.bookapi.presentation.screen.signIn.SignInScreenDirections
import uz.gita.bookapi.presentation.screen.signIn.SignInViewModel
import uz.gita.bookapi.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class SignInViewModelImpl @Inject constructor(
    private val navigator: Navigator,
    private val useCase: SignInUseCase
) : SignInViewModel, ViewModel() {

    override val failureFlow: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val successFlow: MutableSharedFlow<Unit> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val loading: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val hasConnection: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val isValidFlow: MutableSharedFlow<Boolean> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun signIn(password: String, phone: String) {
        Log.d("zzzz", phone)
        viewModelScope.launch {
            Log.d("zzz", "ViewModel: isValid = ${useCase.checkSignInput(password, phone)}-- sign In")
            val isValidWithPhone = useCase.checkSignInput(password, phone)
            if (isValidWithPhone.first) {
                isValidFlow.emit(true)
                val signInRequest = SignInRequest(isValidWithPhone.second, password)
                useCase.signIn(signInRequest).collect { resultData ->
                    Log.d("zzz", "ViewModel: resultData = $resultData")
                    when (resultData) {
                        is ResultData.Failure -> failureFlow.emit(resultData.message)
                        is ResultData.HasConnection -> {
                            Log.d("zzz", "ViewModel: insided when hasConnection = ${resultData.state}")
                            hasConnection.emit(resultData.state)
                        }
                        is ResultData.Loading -> loading.emit(resultData.state)
                        is ResultData.Success -> successFlow.emit(Unit)
                    }
                }
            } else {
                //note user: make sure password length is at least 6 and number is correct
                isValidFlow.emit(false)
            }
        }
    }

    override fun openRegister() {
        viewModelScope.launch {
            navigator.navigateTo(SignInScreenDirections.actionSignInScreenToSignUpScreen())
        }
    }

    override fun openVerify() {
        viewModelScope.launch {
            navigator.navigateTo(SignInScreenDirections.actionSignInScreenToSignInVerifyScreen())
        }
    }

    override fun openHome() {
        viewModelScope.launch {
            navigator.navigateTo(SignInScreenDirections.actionSignInScreenToBaseScreen())
        }
    }
}