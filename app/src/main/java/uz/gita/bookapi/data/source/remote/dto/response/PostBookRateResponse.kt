package uz.gita.bookapi.data.source.remote.dto.response

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/07
Time: 22:12
 */
data class PostBookRateResponse(
    val bookId: Int,
    val isLike: Boolean
)
