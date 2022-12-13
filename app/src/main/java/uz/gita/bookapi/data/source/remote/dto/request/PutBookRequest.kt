package uz.gita.bookapi.data.source.remote.dto.request

data class PutBookRequest(
    var id: Int,
    val title: String,
    val author: String,
    val description: String,
    val pageCount: Int
)
