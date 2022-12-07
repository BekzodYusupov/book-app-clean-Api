package uz.gita.bookapi.data.source.remote.dto.request

data class PostBookRequest(
    val title: String,
    val author: String,
    val description: String,
    val pageCount: Int
)
