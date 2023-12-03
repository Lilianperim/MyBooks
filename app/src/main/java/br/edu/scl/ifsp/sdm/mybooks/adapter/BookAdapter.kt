package br.edu.scl.ifsp.sdm.mybooks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.scl.ifsp.sdm.mybooks.data.Book
import br.edu.scl.ifsp.sdm.mybooks.databinding.BookItemBinding

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>(), Filterable {

    private lateinit var binding: BookItemBinding
    var booksList = ArrayList<Book>()
    var booksListFilterable = ArrayList<Book>()
    var listener: BookListener? = null

    fun updateList(newList: ArrayList<Book>) {
        booksList = newList
        booksListFilterable = booksList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: BookListener) {
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
        holder.title.text = booksListFilterable[position].title
        holder.genre.text = booksListFilterable[position].genre
    }

    override fun getItemCount() = booksListFilterable.size

    inner class BookViewHolder(view: BookItemBinding) : RecyclerView.ViewHolder(view.root) {
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    booksListFilterable = booksList
                else {
                    val resultList = ArrayList<Book>()
                    for (row in booksList)
                        if (row.title.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    booksListFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = booksListFilterable
                return filterResults

            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                booksListFilterable = p1?.values as ArrayList<Book>
                notifyDataSetChanged()
            }
        }
    }
}