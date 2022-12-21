package uz.gita.bookapi.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.domain.usecase.HomeUseCase
import uz.gita.bookapi.presentation.screen.home.HomeViewModel
import uz.gita.bookapi.utils.LoadingType
import uz.gita.bookapi.utils.ResultData
import uz.gita.bookapi.utils.mLog
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

    override val booksFlow: MutableSharedFlow<List<BookResponseEntity>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val bookLiveData = MutableLiveData<List<BookResponseEntity>>()

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

    init {
        viewModelScope.launch {
            homeUseCase.getOfflineBooks().collectLatest {
                booksFlow.emit(ArrayList(it))
                bookLiveData.value = ArrayList(it)
            }
        }

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

    override fun postBook(bookResponseEntity: BookResponseEntity) {
        viewModelScope.launch {
            homeUseCase.postBook(bookResponseEntity).collect { resultData ->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(resultData.data!!)
                }
            }
        }
    }

    override fun changeFav(bookResponseEntity: BookResponseEntity) {
        viewModelScope.launch {
            homeUseCase.changeFav(bookResponseEntity).collectLatest { resultData ->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(resultData.data!!)
                }
            }
        }
    }

    override fun putBook(bookResponseEntity: BookResponseEntity) {
        viewModelScope.launch {
            homeUseCase.putBook(bookResponseEntity).collect { resultData ->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> successFlow.emit(resultData.data!!)
                }
            }
        }
    }

    override fun deleteBook(bookResponseEntity: BookResponseEntity) {
        viewModelScope.launch {
            homeUseCase.deleteBook(bookResponseEntity).collect { resultData ->
                when (resultData) {
                    is ResultData.Failure -> failureFlow.emit(resultData.message)
                    is ResultData.HasConnection -> hasConnection.emit(resultData.state)
                    is ResultData.Loading -> loading.emit(resultData.state)
                    is ResultData.Success -> {
                        mLog("success from viewModel ${resultData.data}")
                        successFlow.emit(resultData.data!!)
                    }
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
}
