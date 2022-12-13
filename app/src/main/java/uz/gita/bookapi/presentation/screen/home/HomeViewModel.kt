package uz.gita.bookapi.presentation.screen.home

import uz.gita.bookapi.data.source.remote.dto.request.ChangeFavRequest
import uz.gita.bookapi.data.source.remote.dto.request.DeleteRequest
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.presentation.viewModel.BaseViewModel

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/06
Time: 16:53
 */
interface HomeViewModel : BaseViewModel {
    fun getBooks()
    fun postBook(postBookRequest: PostBookRequest)
    fun changeFav(changeFavRequest: ChangeFavRequest)
    fun putBook(putBookRequest: PutBookRequest)
    fun deleteBook(deleteRequest: DeleteRequest)
}