package uz.gita.bookapi.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.dto.response.BookResponse
import uz.gita.bookapi.data.source.remote.dto.response.BooksResponse
import uz.gita.bookapi.data.source.remote.dto.response.PostBookResponse
import uz.gita.bookapi.data.source.remote.dto.response.PutBookResponse
import uz.gita.bookapi.utils.ResultData

interface BookRepository {
    fun postBook(postBookRequest: PostBookRequest): Flow<ResultData<PostBookResponse>>
    fun getBooks(): Flow<ResultData<BooksResponse>>
    fun deleteBook(deleteRequest: DeleteRequest): Flow<ResultData<BookResponse>>
    fun putBook(putBookRequest: PutBookRequest): Flow<ResultData<PutBookResponse>>
    fun changeFav(bookId: Int): Flow<ResultData<BookResponse>>
}