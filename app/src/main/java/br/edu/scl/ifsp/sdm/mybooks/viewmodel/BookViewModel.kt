package br.edu.scl.ifsp.sdm.mybooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.scl.ifsp.sdm.mybooks.data.Book
import br.edu.scl.ifsp.sdm.mybooks.data.BookDataBase
import br.edu.scl.ifsp.sdm.mybooks.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository
    lateinit var book: LiveData<Book>
    var allBooks: LiveData<List<Book>>

    init {
        val dao = BookDataBase.getDatabase(application).bookDAO()
        repository = BookRepository(dao)
        allBooks = repository.getAllBooks()
    }

    fun insert(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(book)
    }

    fun update(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(book)
    }

    fun delete(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(book)
    }

    fun getBookById(id: Int) {
        viewModelScope.launch {
            book = repository.getBookById(id)
        }
    }
}