package uz.akbarovdev.safechat.core.wrapper

sealed class Handler<out T> {
    data class Success<T>(val data: T) : Handler<T>()
    data class Error(val message: String, val code: Int? = null) : Handler<Nothing>()
}
