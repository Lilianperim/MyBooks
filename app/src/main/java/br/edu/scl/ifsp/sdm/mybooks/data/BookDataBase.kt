package br.edu.scl.ifsp.sdm.mybooks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class BookDataBase : RoomDatabase() {
    abstract fun bookDAO(): BookDAO

    companion object {
        @Volatile
        private var INSTANCE: BookDataBase? = null

        fun getDatabase(context: Context): BookDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDataBase::class.java,
                    "mybooks.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}