package uz.gita.bookapi.data.source.remote.dto.request

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/07
Time: 22:11
 */
data class PostBookRateRequest(
    val bookId: Int,
    val isLike: Boolean
)
