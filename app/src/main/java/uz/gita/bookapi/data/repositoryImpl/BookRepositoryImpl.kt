package uz.gita.bookapi.data.repositoryImpl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.dto.response.BookResponse
import uz.gita.bookapi.data.source.remote.dto.response.BooksResponse
import uz.gita.bookapi.data.source.remote.dto.response.PostBookResponse
import uz.gita.bookapi.data.source.remote.dto.response.PutBookResponse
import uz.gita.bookapi.data.source.remote.serivce.BookApi
import uz.gita.bookapi.domain.repository.BookRepository
import uz.gita.bookapi.utils.ResultData
import uz.gita.bookapi.utils.hasConnection
import javax.inject.Inject


class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    @ApplicationContext private val context: Context
) : BookRepository {
    override fun postBook(postBookRequest: PostBookRequest): Flow<ResultData<PostBookResponse>> =
        flow<ResultData<PostBookResponse>> {
            if (hasConnection(context)) {
                emit(ResultData.HasConnection(true))
                val response = bookApi.postBook(postBookRequest)
                emit(ResultData.Loading(true))

                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResultData.Success(it))
                        emit(ResultData.Loading(false))
                    }
                } else emit(ResultData.Failure(response.message()))
            } else emit(ResultData.HasConnection(false))
        }.catch { error ->
            error.message?.let { message ->
                emit(ResultData.Failure(message))
            }
        }.flowOn(IO)

    override fun getBooks(): Flow<ResultData<BooksResponse>> = flow<ResultData<BooksResponse>> {
        if (hasConnection(context)) {
            emit(ResultData.HasConnection(true))
            val response = bookApi.getBooks()
            emit(ResultData.Loading(true))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultData.Success(it))
                }
                emit(ResultData.Loading(false))
            } else emit(ResultData.Failure(response.message()))
        } else emit(ResultData.HasConnection(false))
    }.catch {

    }.flowOn(IO)

    override fun deleteBook(deleteRequest: DeleteRequest): Flow<ResultData<BookResponse>> {
        TODO("Not yet implemented")
    }

    override fun putBook(putBookRequest: PutBookRequest): Flow<ResultData<PutBookResponse>> {
        TODO("Not yet implemented")
    }

    override fun changeFav(bookId: Int): Flow<ResultData<BookResponse>> {
        TODO("Not yet implemented")
    }

}