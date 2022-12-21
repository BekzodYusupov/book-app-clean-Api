package uz.gita.bookapi.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/13
Time: 18:52
 */

@Entity(tableName = "book")
data class BookResponseEntity(
    var id:Int? = null,
    val title: String,
    val author: String,
    @PrimaryKey(autoGenerate = false)
    val description: String,
    val pageCount: Int,
    var state: State,
    var fav: Boolean = false
)