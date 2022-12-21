package uz.gita.bookapi.data.source.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/13
Time: 19:10
 */

@Dao
interface BookDao {
    @Insert
    suspend fun insert(data: BookResponseEntity)

    @Insert
    suspend fun insert(list: List<BookResponseEntity>)

    @Update
    suspend fun update(data: BookResponseEntity)

    @Delete
    suspend fun delete(data: BookResponseEntity)

    @Query("DELETE FROM book")
    suspend fun delete()

    @Query("DELETE FROM book WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM book WHERE state != :deleted")
    fun getBooks(deleted: String = "LocalDeleted"): Flow<List<BookResponseEntity>>

    @Query("SELECT * FROM book")
    suspend fun getAllBook(): List<BookResponseEntity>
}
