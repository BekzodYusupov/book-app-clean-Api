package uz.gita.bookapi.presentation.screen.home

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.presentation.viewModel.BaseViewModel

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/06
Time: 16:53
 */
interface HomeViewModel : BaseViewModel {
    val booksFlow: Flow<List<BookResponseEntity>>
    val bookLiveData:LiveData<List<BookResponseEntity>>

    fun postBook(bookResponseEntity: BookResponseEntity)
    fun changeFav(bookResponseEntity: BookResponseEntity)
    fun putBook(bookResponseEntity: BookResponseEntity)
    fun deleteBook(bookResponseEntity: BookResponseEntity)

    fun getBooks()
}