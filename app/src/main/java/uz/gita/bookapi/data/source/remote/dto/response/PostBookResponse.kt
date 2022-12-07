package uz.gita.bookapi.data.source.remote.dto.response

data class PostBookResponse(
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val pageCount: Int,
    val fav: Boolean = false
)
