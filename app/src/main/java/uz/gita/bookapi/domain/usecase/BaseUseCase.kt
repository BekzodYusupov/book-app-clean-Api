package uz.gita.bookapi.domain.usecase

/**
Created: Bekzod Yusupov
Date:
Time: 18:12
 */
interface BaseUseCase {
    fun reformatPhone(phone: String): String
}