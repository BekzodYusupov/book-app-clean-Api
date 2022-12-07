package uz.gita.bookapi.domain.usecase.impl

import uz.gita.bookapi.domain.usecase.BaseUseCase
import javax.inject.Inject

class BaseUseCaseImpl @Inject constructor(): BaseUseCase {
    override fun reformatPhone(phone: String): String =
        phone.filter { !it.isWhitespace() }
}