package uz.gita.bookapi.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.ChangeFavRequest
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.dto.response.*
import uz.gita.bookapi.domain.repository.BookRepository
import uz.gita.bookapi.domain.usecase.HomeUseCase
import uz.gita.bookapi.utils.ResultData
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val bookRepository: BookRepository
) : HomeUseCase {
    override fun getBooks(): Flow<ResultData<BooksResponse>> = bookRepository.getBooks()
    override fun changeFav(changeFavRequest: ChangeFavRequest): Flow<ResultData<ChangeFavResponse>> =
        bookRepository.changeFav(changeFavRequest)

    override fun putBook(putBookRequest: PutBookRequest): Flow<ResultData<PutBookResponse>> =
        bookRepository.putBook(putBookRequest)

    override fun postBook(postBookRequest: PostBookRequest): Flow<ResultData<PostBookResponse>> =
        bookRepository.postBook(postBookRequest)

    override fun deleteBook(deleteRequest: DeleteRequest): Flow<ResultData<BookResponse>> =
        bookRepository.deleteBook(deleteRequest)
}