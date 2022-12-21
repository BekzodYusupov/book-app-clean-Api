package uz.gita.bookapi.presentation.screen.signInVerify

import uz.gita.bookapi.data.source.remote.dto.request.VerifyRequest
import uz.gita.bookapi.presentation.viewModel.BaseViewModel

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/15
Time: 12:07
 */

interface SignInVerifyViewModel:BaseViewModel {
    fun openHomeScreen()
    fun signInVerify(verifyRequest: VerifyRequest)
}