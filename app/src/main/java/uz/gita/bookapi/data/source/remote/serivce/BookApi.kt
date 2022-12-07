package uz.gita.bookapi.data.source.remote.serivce

import retrofit2.Response
import retrofit2.http.*
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.dto.response.BookResponse
import uz.gita.bookapi.data.source.remote.dto.response.BooksResponse
import uz.gita.bookapi.data.source.remote.dto.response.PostBookResponse
import uz.gita.bookapi.data.source.remote.dto.response.PutBookResponse

interface BookApi {
    @POST("book")
    suspend fun postBook(
//        @Header("Authorization") bearerToken: String,
        @Body postBookRequest: PostBookRequest
    ): Response<PostBookResponse>

    @GET("books")
    suspend fun getBooks(
//        @Header("Authorization") bearerToken: String,
    ): Response<BooksResponse>

    @DELETE("book")
    suspend fun deleteBook(
//        @Header("Authorization") bearerToken: String,
        @Body deleteRequest: DeleteRequest//to'girlassh kere bu xato
    ): Response<BookResponse>

    @PUT("book")
    suspend fun putBook(
//        @Header("Authorization") bearerToken: String,
        @Body putBookRequest: PutBookRequest
    ): Response<PutBookResponse>

    @POST("book/change-fav")
    suspend fun changeFav(
//        @Header("Authorization") bearerToken: String,
        @Body bookId: Int
    ): Response<BookResponse>

}