package uz.gita.bookapi.data.repositoryImpl

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bookapi.data.mapper.convertToEntity
import uz.gita.bookapi.data.source.local.dao.BookDao
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.data.source.local.entity.State
import uz.gita.bookapi.data.source.remote.dto.request.ChangeFavRequest
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.serivce.BookApi
import uz.gita.bookapi.domain.repository.BookRepository
import uz.gita.bookapi.utils.ConnectionUtil
import uz.gita.bookapi.utils.LoadingType
import uz.gita.bookapi.utils.ResultData
import uz.gita.bookapi.utils.mLog
import javax.inject.Inject


class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao,
    private val connectionUtil: ConnectionUtil
) : BookRepository {
    override fun getOfflineBooks(): Flow<List<BookResponseEntity>> =
        bookDao.getBooks()

    override fun postBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<String>> =
        flow<ResultData<String>> {
            mLog("Repo: Post is called")
            if (connectionUtil.hasConnection()) {
                mLog("Repo: Post: has connection true")
                emit(ResultData.HasConnection(true))
                emit(ResultData.Loading(LoadingType(fullScreen = true)))
                val postBookRequest = PostBookRequest(
                    bookResponseEntity.title,
                    bookResponseEntity.author,
                    bookResponseEntity.description,
                    bookResponseEntity.pageCount
                )
                val response = bookApi.postBook(postBookRequest)

                if (response.isSuccessful) {
                    mLog("Repo: Post: has connection true: reposponse successful resposne = ${response.body()}")

                    response.body()?.let {
                        val message = "Successfully posted"
                        emit(ResultData.Success(message))
                        emit(ResultData.Loading(LoadingType(fullScreen = false)))
                        bookResponseEntity.state = State.UpToDate
                        bookResponseEntity.id = it.id
                        bookDao.insert(bookResponseEntity)
                    }
                } else emit(ResultData.Failure(response.message()))
            } else {
                emit(ResultData.HasConnection(false))
                bookResponseEntity.state = State.LocalAdded
                bookDao.insert(bookResponseEntity)
            }
        }.catch { error ->
            error.message?.let { message -> emit(ResultData.Failure(message)) }
        }.flowOn(IO)

    override fun getBooks(): Flow<ResultData<String>> = flow<ResultData<String>> {
        if (connectionUtil.hasConnection()) {
            val localBooks = bookDao.getAllBook()
            bookDao.delete()//clear database
            localBooks.onEach {
                mLog("data state - ${it.state}")
                when (it.state) {
                    State.UpToDate -> { }
                    State.LocalEdited -> {
                        mLog("state - delete - updating")
                        it.state = State.UpToDate
                        putBook(it).collect{
                            when (it) {
                                is ResultData.Failure -> mLog("fail = ${it.message}")
                                is ResultData.HasConnection -> mLog("hasConncetion = ${it.state}")
                                is ResultData.Loading -> mLog("hasConncetion = ${it.state}")
                                is ResultData.Success -> mLog("success = ${it.data}")
                            }
                        }
                    }
                    State.LocalDeleted -> {
                        mLog("state - delete - updating")
                        it.state = State.UpToDate
                        deleteBook(it).collect{
                            when (it) {
                                is ResultData.Failure -> mLog("fail = ${it.message}")
                                is ResultData.HasConnection -> mLog("hasConncetion = ${it.state}")
                                is ResultData.Loading -> mLog("hasConncetion = ${it.state}")
                                is ResultData.Success -> mLog("success = ${it.data}")
                            }
                        }
                    }
                    State.LocalAdded -> {
                        mLog("state - add - updating")
                        it.state = State.UpToDate
                        postBook(it).collect{
                            when (it) {
                                is ResultData.Failure -> mLog("fail = ${it.message}")
                                is ResultData.HasConnection -> mLog("hasConncetion = ${it.state}")
                                is ResultData.Loading -> mLog("hasConncetion = ${it.state}")
                                is ResultData.Success -> mLog("success = ${it.data}")
                            }
                        }
                    }
                }
            }
            emit(ResultData.Loading(LoadingType(fullScreen = true)))
            emit(ResultData.HasConnection(true))
            val response = bookApi.getBooks()
            if (response.isSuccessful) {
                mLog("Success")
                response.body()?.let {
                    mLog("not null convert to entity")
                    val tempList = ArrayList<BookResponseEntity>()
                    it.onEach { booksResponseItem -> tempList.add(booksResponseItem.convertToEntity()) }
                    mLog("emit success")
                    bookDao.delete()
                    bookDao.insert(tempList)
                    val message = "Your contacts Up to date"
                    emit(ResultData.Success(message))
                    emit(ResultData.Loading(LoadingType(fullScreen = false)))
                }
            } else emit(ResultData.Failure(response.message()))
        } else emit(ResultData.HasConnection(false))
    }.catch { error ->
        mLog("catch error = $error")
        error.message?.let { emit(ResultData.Failure(it)) }
    }.flowOn(IO)

     override fun deleteBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>> =
         flow<ResultData<Unit>> {
             mLog("Repo:Delete called")
             if (connectionUtil.hasConnection()) {
                 mLog("Repo:Delete called->hasConnectin true")
                 emit(ResultData.Loading(LoadingType(fullScreen = true)))
                 emit(ResultData.HasConnection(true))
                 val response = bookApi.deleteBook(DeleteRequest(bookResponseEntity.id.toString()))
                 if (response.isSuccessful) {
                     mLog("Repo:Delete called->hasConnectin true->response success")
                     response.body()?.let {
                         emit(ResultData.Success(Unit))
                         emit(ResultData.Loading(LoadingType(fullScreen = false)))
                         bookDao.delete(bookResponseEntity)
                     }
                 } else emit(ResultData.Failure(response.message()))
             } else {
                 mLog("Repo:Delete called->hasConnection false")
                 emit(ResultData.HasConnection(false))
                 bookResponseEntity.state = State.LocalDeleted
                 bookDao.update(bookResponseEntity)
             }
         }.catch { error ->
             error.message?.let { emit(ResultData.Failure(it)) }
         }.flowOn(IO)


     override fun putBook(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>> = flow<ResultData<Unit>> {
         mLog("Repo:putBook called")
         if (connectionUtil.hasConnection()) {
             mLog("Repo:putBook called->hasCon true")
             emit(ResultData.Loading(LoadingType(fullScreen = true)))
             emit(ResultData.HasConnection(true))
             val response = bookApi.putBook(PutBookRequest(bookResponseEntity.id!!,bookResponseEntity.title,bookResponseEntity.author,bookResponseEntity.description,bookResponseEntity.pageCount))
             if (response.isSuccessful) {
                 mLog("Repo:putBook called->hasCon true->success")
                 response.body()?.let {
                     emit(ResultData.Success(Unit))
                     emit(ResultData.Loading(LoadingType(fullScreen = false)))
                     bookDao.update(bookResponseEntity)
                 }
             } else {
                 emit(ResultData.Failure(response.message()))
                 bookResponseEntity.state = State.LocalEdited
                 bookDao.update(bookResponseEntity)
                 mLog("Repo:putBook called->hasCon true->success false")
             }
         } else {
             mLog("Repo:putBook called->hasCon false")
             emit(ResultData.HasConnection(false))
             bookResponseEntity.state = State.LocalEdited
             bookDao.update(bookResponseEntity)
         }
     }.catch { error ->
         error.message?.let { emit(ResultData.Failure(it)) }
     }.flowOn(IO)

     override fun changeFav(bookResponseEntity: BookResponseEntity): Flow<ResultData<Unit>> =
         flow<ResultData<Unit>> {
             mLog("Repo:changeFav called parameter data = booksResponseEntity = $bookResponseEntity")
             if (connectionUtil.hasConnection()) {
                 mLog("Repo:changeFav called->hasConn true")
                 emit(ResultData.HasConnection(true))
                 emit(ResultData.Loading(LoadingType(bookItem = true)))
                 val response = bookApi.changeFav(ChangeFavRequest(bookResponseEntity.id!!))
                 when (val code = response.code()) {
                     in 100..199 -> {
                         mLog("Repo:changeFav called->hasConn true->success false code = $code")
                         emit(ResultData.Failure(code.toString()))
                     }
                     in 200..299 -> {
                         response.body()?.let {
                             mLog("Repo:changeFav called->hasConn true->success true code = $code and response = ${it.message}")
                             emit(ResultData.Success(Unit))
                             bookResponseEntity.fav = !bookResponseEntity.fav
                             bookDao.update(bookResponseEntity)
                         }
                     }
                     in 300..399 -> {
                         mLog("Repo:changeFav called->hasConn true->success false code = $code")
                         emit(ResultData.Failure(code.toString()))
                     }
                     in 400..499 -> {
                         mLog("Repo:changeFav called->hasConn true->success false code = $code")
                         emit(ResultData.Failure(code.toString()))
                     }
                     in 500..599 -> {
                         mLog("Repo:changeFav called->hasConn true->success false code = $code")
                         emit(ResultData.Failure(code.toString()))
                     }
                 }
                 emit(ResultData.Loading(LoadingType(bookItem = false)))
             } else {
                 emit(ResultData.HasConnection(false))
                 bookResponseEntity.fav = !bookResponseEntity.fav
                 bookResponseEntity.state = State.LocalEdited
                 bookDao.update(bookResponseEntity)
             }
         }.catch { error ->
             error.message?.let { emit(ResultData.Failure(it)) }
         }.flowOn(IO)

}