package uz.gita.bookapi.data.source.remote.dto.request

data class SignUpRequest(
    var phone: String,
    val password: String,
    val lastName: String,
    val firstName: String
)
