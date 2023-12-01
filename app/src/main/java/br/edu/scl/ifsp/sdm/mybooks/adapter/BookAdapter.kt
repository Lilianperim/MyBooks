package br.edu.scl.ifsp.sdm.mybooks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.scl.ifsp.sdm.mybooks.data.Book
import br.edu.scl.ifsp.sdm.mybooks.databinding.BookItemBinding

class BookAdapter: RecyclerView.Adapter<BookAdapter.BookViewHolder>()  {

    private lateinit var binding: BookItemBinding
    var booksList = ArrayList<Book>()
    var booksListFilterable = ArrayList<Book>()
    var listener: BookListener?=null

    fun updateList(newList: ArrayList<Book> ){
        booksList = newList
        booksListFilterable = booksList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: BookListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder {
        binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.title.text = booksList[position].title
        holder.genre.text = booksList[position].genre
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    inner class BookViewHolder(view:BookItemBinding): RecyclerView.ViewHolder(view.root) {
        val title = view.title
        val genre = view.genre
        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface BookListener {
        fun onItemClick(pos: Int)
    }
}