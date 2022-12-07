package uz.gita.bookapi.utils

/*sealed class ResultData<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoading: Boolean? = null,
    val hasConnection: Boolean? = null
) {
    class Success<T>(data: T?) : ResultData<T>(data = data)
    class Failure<T>(message: String) : ResultData<T>(message = message)
    class Loading<T>(state: Boolean) : ResultData<T>(isLoading = state)
    class HasConnection<T>(state: Boolean) : ResultData<T>(hasConnection = state)
}*/

sealed interface ResultData<T> {
    class Success<T>(val data: T?) : ResultData<T>
    class Failure<T>(val message: String) : ResultData<T>
    class Loading<T>(val state: Boolean) : ResultData<T>
    class HasConnection<T>(val state: Boolean) : ResultData<T>
}