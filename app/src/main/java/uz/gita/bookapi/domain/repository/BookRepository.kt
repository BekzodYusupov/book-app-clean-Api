package uz.gita.bookapi.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.ChangeFavRequest
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.dto.response.*
import uz.gita.bookapi.utils.ResultData

interface BookRepository {
    fun postBook(postBookRequest: PostBookRequest): Flow<ResultData<PostBookResponse>>
    fun getBooks(): Flow<ResultData<BooksResponse>>
    fun deleteBook(deleteRequest: DeleteRequest): Flow<ResultData<BookResponse>>
    fun putBook(putBookRequest: PutBookRequest): Flow<ResultData<PutBookResponse>>
    fun changeFav(changeFavRequest: ChangeFavRequest): Flow<ResultData<ChangeFavResponse>>
}