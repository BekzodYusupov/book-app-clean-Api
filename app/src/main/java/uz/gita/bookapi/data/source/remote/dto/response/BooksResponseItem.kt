package uz.gita.bookapi.data.source.remote.dto.response

data class BooksResponseItem(
    val id: Int?,
    val title: String,
    val author: String,
    val description: String,
    val pageCount: Int,
    var fav: Boolean
)