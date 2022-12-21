package uz.gita.bookapi.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.utils.ResultData

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/07
Time: 13:09
 */
interface HomeUseCase {
    fun getBooks(): Flow<ResultData<String>>
    fun getOfflineBooks():Flow<List<BookResponseEntity>>

    fun changeFav(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>>
    fun putBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>>
    fun postBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<String>>
    fun deleteBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>>
}