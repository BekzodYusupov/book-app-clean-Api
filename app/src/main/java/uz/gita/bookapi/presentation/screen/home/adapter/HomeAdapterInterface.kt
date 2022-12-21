package uz.gita.bookapi.presentation.screen.home.adapter

import uz.gita.bookapi.data.source.local.entity.BookResponseEntity

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/21
Time: 13:24
 */
interface HomeAdapterInterface {
    fun triggerBookMarkClickListener(block: (BookResponseEntity) -> Unit)
    fun triggerDeleteClickListener(block: (BookResponseEntity) -> Unit)
    fun triggerEditClickListener(block: (BookResponseEntity) -> Unit)

    fun megaListener(bookMarkBlock: (BookResponseEntity) -> Unit,deleteBlock: (BookResponseEntity) -> Unit, editBlock: (BookResponseEntity) -> Unit)
}
