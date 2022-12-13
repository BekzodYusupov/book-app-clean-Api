package uz.gita.bookapi.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.bookapi.data.source.remote.dto.request.ChangeFavRequest
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.domain.usecase.HomeUseCase
import uz.gita.bookapi.presentation.screen.home.HomeViewModel
import uz.gita.bookapi.utils.LoadingType
import uz.gita.bookapi.utils.ResultData
import javax.inject.Inject

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/07
Time: 17:20
 */
@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val homeUseCase: HomeUseCase
) : HomeViewModel, ViewModel() {

    init {
        viewModelScope.launch {
            homeUseCase.getBooks().collectLatest { resultData ->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(resultData.data!!)
                }
            }
        }
    }

    override fun getBooks() {
        viewModelScope.launch {
            homeUseCase.getBooks().collectLatest { resultData ->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(resultData.data!!)
                }
            }
        }
    }

    override fun postBook(postBookRequest: PostBookRequest) {
        viewModelScope.launch {
            homeUseCase.postBook(postBookRequest).collect { resultData ->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(resultData.data!!)
                }
            }
        }
    }

    override fun changeFav(changeFavRequest: ChangeFavRequest) {
        viewModelScope.launch {
            homeUseCase.changeFav(changeFavRequest).collectLatest { resultData ->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(resultData.data!!)
                }
            }
        }
    }

    override fun putBook(putBookRequest: PutBookRequest) {
        viewModelScope.launch {
            homeUseCase.putBook(putBookRequest).collect{ resultData->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(resultData.data!!)
                }
            }
        }
    }

    override fun deleteBook(deleteRequest: DeleteRequest) {
        viewModelScope.launch {
            homeUseCase.deleteBook(deleteRequest).collect{ resultData->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(resultData.data!!)
                }
            }
        }
    }

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

}
