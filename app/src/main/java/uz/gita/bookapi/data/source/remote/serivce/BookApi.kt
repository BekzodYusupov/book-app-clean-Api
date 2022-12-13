package uz.gita.bookapi.data.source.remote.serivce

import retrofit2.Response
import retrofit2.http.*
import uz.gita.bookapi.data.source.remote.dto.request.ChangeFavRequest
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.dto.response.*

interface BookApi {
    @POST("book")
    suspend fun postBook(
        @Body postBookRequest: PostBookRequest
    ): Response<PostBookResponse>

    @GET("books")
    suspend fun getBooks(
    ): Response<BooksResponse>

//    @DELETE("book")
//    suspend fun deleteBook(
//        @Body deleteRequest: DeleteRequest//to'girlassh kere bu xato
//    ): Response<BookResponse>

    @HTTP(method = "DELETE", path = "book", hasBody = true)
    suspend fun deleteBook(
        @Body deleteRequest: DeleteRequest
    ):Response<BookResponse>

    @PUT("book")
    suspend fun putBook(
        @Body putBookRequest: PutBookRequest
    ): Response<PutBookResponse>

    @POST("book/change-fav")
    suspend fun changeFav(
        @Body ChangeFavRequest: ChangeFavRequest
    ): Response<ChangeFavResponse>

}