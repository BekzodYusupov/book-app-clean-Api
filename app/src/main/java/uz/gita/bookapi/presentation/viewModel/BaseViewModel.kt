package uz.gita.bookapi.presentation.viewModel

import kotlinx.coroutines.flow.MutableSharedFlow
import uz.gita.bookapi.utils.LoadingType

/**
Created: Bekzod Yusupov
Date: 05.12.2022
Time: 19:08
 */
interface BaseViewModel {
    val failureFlow: MutableSharedFlow<String>
    val successFlow: MutableSharedFlow<Any>
    val loading: MutableSharedFlow<LoadingType>
    val hasConnection: MutableSharedFlow<Boolean>
    val isValidFlow: MutableSharedFlow<Boolean>
}