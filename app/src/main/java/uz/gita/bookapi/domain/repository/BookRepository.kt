package uz.gita.bookapi.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.utils.ResultData

interface BookRepository {
    fun getOfflineBooks():Flow<List<BookResponseEntity>>
    fun postBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<String>>
    fun getBooks(): Flow<ResultData<String>>
    fun deleteBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>>
    fun putBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>>
    fun changeFav(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>>
}