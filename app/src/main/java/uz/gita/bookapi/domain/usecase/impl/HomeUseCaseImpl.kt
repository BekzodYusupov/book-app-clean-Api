package uz.gita.bookapi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.domain.repository.BookRepository
import uz.gita.bookapi.domain.usecase.HomeUseCase
import uz.gita.bookapi.utils.ResultData
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
) : HomeUseCase {
    override fun getBooks(): Flow<ResultData<String>> = bookRepository.getBooks()
    override fun getOfflineBooks(): Flow<List<BookResponseEntity>> = bookRepository.getOfflineBooks()

    override fun postBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<String>> =
        bookRepository.postBook(bookResponseEntity)

    override fun changeFav(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>> =
        bookRepository.changeFav(bookResponseEntity)

    override fun putBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>> =
        bookRepository.putBook(bookResponseEntity)
    override fun deleteBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>> =
        bookRepository.deleteBook(bookResponseEntity)
}