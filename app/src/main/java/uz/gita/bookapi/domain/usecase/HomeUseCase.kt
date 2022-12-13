package uz.gita.bookapi.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.remote.dto.request.ChangeFavRequest
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.dto.response.*
import uz.gita.bookapi.utils.ResultData

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/07
Time: 13:09
 */
interface HomeUseCase {
    fun getBooks(): Flow<ResultData<BooksResponse>>
    fun changeFav(changeFavRequest: ChangeFavRequest): Flow<ResultData<ChangeFavResponse>>
    fun putBook(putBookRequest: PutBookRequest): Flow<ResultData<PutBookResponse>>
    fun postBook(postBookRequest: PostBookRequest): Flow<ResultData<PostBookResponse>>
    fun deleteBook(deleteRequest: DeleteRequest): Flow<ResultData<BookResponse>>
}