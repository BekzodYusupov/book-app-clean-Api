package uz.gita.bookapi.presentation.viewModel

import kotlinx.coroutines.flow.MutableSharedFlow

/**
Created: Bekzod Yusupov
Date: 05.12.2022
Time: 19:08
 */
interface BaseViewModel {
    val failureFlow: MutableSharedFlow<String>
    val successFlow: MutableSharedFlow<Unit>
    val loading: MutableSharedFlow<Boolean>
    val hasConnection: MutableSharedFlow<Boolean>
    val isValidFlow: MutableSharedFlow<Boolean>
}