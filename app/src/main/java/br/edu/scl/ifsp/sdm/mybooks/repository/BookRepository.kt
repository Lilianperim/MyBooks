package br.edu.scl.ifsp.sdm.mybooks.repository

import androidx.lifecycle.LiveData
import br.edu.scl.ifsp.sdm.mybooks.data.Book
import br.edu.scl.ifsp.sdm.mybooks.data.BookDAO

class BookRepository(private val bookDAO: BookDAO) {

    suspend fun insert(book: Book) = bookDAO.insert(book)

    suspend fun update(book: Book) = bookDAO.update(book)

    suspend fun delete(book: Book) = bookDAO.delete(book)

    fun getAllBooks(): LiveData<List<Book>> = bookDAO.getAllBooks()

    fun getBookById(id: Int): LiveData<Book> = bookDAO.getBookById(id)
}