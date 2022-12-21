package uz.gita.bookapi.data.mapper

import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.data.source.local.entity.State
import uz.gita.bookapi.data.source.remote.dto.response.BooksResponseItem

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/13
Time: 20:23
 */

fun BooksResponseItem.convertToEntity(): BookResponseEntity {
    return BookResponseEntity(id!!, title, author, description, pageCount, State.UpToDate, fav)
}

fun BookResponseEntity.convertToResponse(): BooksResponseItem {
    return BooksResponseItem(id, title, author, description, pageCount, fav)
}
