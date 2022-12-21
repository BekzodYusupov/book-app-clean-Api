package uz.gita.bookapi.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.bookapi.data.source.local.dao.BookDao
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity


@Database(entities = [BookResponseEntity::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun bookDao(): BookDao
}