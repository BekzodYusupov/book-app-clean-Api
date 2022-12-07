package uz.gita.bookapi.presentation.screen.signIn

import uz.gita.bookapi.presentation.viewModel.BaseViewModel

interface SignInViewModel:BaseViewModel {

    fun signIn(password: String, phone: String)
    fun openRegister()
    fun openVerify()
    fun openHome()

}