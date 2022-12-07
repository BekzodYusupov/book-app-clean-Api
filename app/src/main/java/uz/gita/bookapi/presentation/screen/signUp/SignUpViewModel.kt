package uz.gita.bookapi.presentation.screen.signUp

import uz.gita.bookapi.data.source.remote.dto.request.SignUpRequest
import uz.gita.bookapi.presentation.viewModel.BaseViewModel

interface SignUpViewModel:BaseViewModel {
    fun openSignIn()
    fun openVerify()
    fun signUp(signUpRequest: SignUpRequest, rePassword: String)
}