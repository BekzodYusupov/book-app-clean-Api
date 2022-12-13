package uz.gita.bookapi.data.repositoryImpl

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bookapi.data.source.remote.dto.request.ChangeFavRequest
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.dto.response.*
import uz.gita.bookapi.data.source.remote.serivce.BookApi
import uz.gita.bookapi.domain.repository.BookRepository
import uz.gita.bookapi.utils.ConnectionUtil
import uz.gita.bookapi.utils.LoadingType
import uz.gita.bookapi.utils.ResultData
import uz.gita.bookapi.utils.mLog
import javax.inject.Inject


class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val connectionUtil: ConnectionUtil
) : BookRepository {
    override fun postBook(postBookRequest: PostBookRequest): Flow<ResultData<PostBookResponse>> =
        flow<ResultData<PostBookResponse>> {
            if (connectionUtil.hasConnection()) {
                emit(ResultData.HasConnection(true))
                emit(ResultData.Loading(LoadingType(fullScreen = true)))
                val response = bookApi.postBook(postBookRequest)

                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResultData.Success(it))
                        emit(ResultData.Loading(LoadingType(fullScreen = false)))
                    }
                } else emit(ResultData.Failure(response.message()))
            } else emit(ResultData.HasConnection(false))
        }.catch { error -> error.message?.let { message -> emit(ResultData.Failure(message)) }
        }.flowOn(IO)

    override fun getBooks(): Flow<ResultData<BooksResponse>> = flow<ResultData<BooksResponse>> {
        if (connectionUtil.hasConnection()) {
            emit(ResultData.Loading(LoadingType(fullScreen = true)))
            emit(ResultData.HasConnection(true))
            val response = bookApi.getBooks()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultData.Success(it))
                    emit(ResultData.Loading(LoadingType(fullScreen = false)))
                }
            } else emit(ResultData.Failure(response.message()))
        } else emit(ResultData.HasConnection(false))
    }.catch { error -> error.message?.let { emit(ResultData.Failure(it)) }
    }.flowOn(IO)

    override fun deleteBook(deleteRequest: DeleteRequest): Flow<ResultData<BookResponse>> = flow<ResultData<BookResponse>> {
        if (connectionUtil.hasConnection()) {
            emit(ResultData.Loading(LoadingType(fullScreen = true)))
            emit(ResultData.HasConnection(true))
            val response = bookApi.deleteBook(deleteRequest)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultData.Success(it))
                    emit(ResultData.Loading(LoadingType(fullScreen = false)))
                }
            } else emit(ResultData.Failure(response.message()))
        } else emit(ResultData.HasConnection(false))
    }.catch { error -> error.message?.let { emit(ResultData.Failure(it)) }
    }.flowOn(IO)

    override fun putBook(putBookRequest: PutBookRequest): Flow<ResultData<PutBookResponse>> = flow<ResultData<PutBookResponse>>{
        if (connectionUtil.hasConnection()) {
            emit(ResultData.Loading(LoadingType(fullScreen = true)))
            emit(ResultData.HasConnection(true))
            val response = bookApi.putBook(putBookRequest)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultData.Success(it))
                    emit(ResultData.Loading(LoadingType(fullScreen = false)))
                }
            } else emit(ResultData.Failure(response.message()))
        } else emit(ResultData.HasConnection(false))
    }.catch { error -> error.message?.let { emit(ResultData.Failure(it)) }
    }.flowOn(IO)

    override fun changeFav(changeFavRequest: ChangeFavRequest): Flow<ResultData<ChangeFavResponse>> =
        flow<ResultData<ChangeFavResponse>> {
            if (connectionUtil.hasConnection()) {
                emit(ResultData.HasConnection(true))
                emit(ResultData.Loading(LoadingType(bookItem = true)))
                val response = bookApi.changeFav(changeFavRequest)
                when (val code = response.code()) {
                    in 100..199 -> {
                        mLog("$code")
                        emit(ResultData.Failure(code.toString()))
                    }
                    in 200..299 -> {
                        response.body()?.let {
                            emit(ResultData.Success(it))
                        }
                    }
                    in 300..399 -> {
                        mLog("$code")
                        emit(ResultData.Failure(code.toString()))
                    }
                    in 400..499 -> {
                        mLog("$code")
                        emit(ResultData.Failure(code.toString()))
                    }
                    in 500..599 -> {
                        mLog("$code")
                        emit(ResultData.Failure(code.toString()))
                    }
                }
                emit(ResultData.Loading(LoadingType(bookItem = false)))
            } else emit(ResultData.HasConnection(false))
        }.catch { error -> error.message?.let { emit(ResultData.Failure(it)) }
        }.flowOn(IO)

}