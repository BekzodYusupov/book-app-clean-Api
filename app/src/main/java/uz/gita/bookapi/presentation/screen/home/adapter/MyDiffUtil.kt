package uz.gita.bookapi.presentation.screen.home.adapter

import androidx.recyclerview.widget.DiffUtil
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/21
Time: 12:19
 */

class MyDiffUtil(
    private val oldList: ArrayList<BookResponseEntity>,
    private val newList: ArrayList<BookResponseEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].fav == newList[newItemPosition].fav &&
        oldList[oldItemPosition].title == newList[newItemPosition].title &&
        oldList[oldItemPosition].description == newList[newItemPosition].description &&
        oldList[oldItemPosition].author == newList[newItemPosition].author &&
//        oldList[oldItemPosition].state == newList[newItemPosition].state &&
        oldList[oldItemPosition].id == newList[newItemPosition].id
}