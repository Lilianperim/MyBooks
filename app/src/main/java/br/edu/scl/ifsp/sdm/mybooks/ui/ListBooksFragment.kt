package br.edu.scl.ifsp.sdm.mybooks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.edu.scl.ifsp.sdm.mybooks.R
import br.edu.scl.ifsp.sdm.mybooks.databinding.FragmentListBooksBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.scl.ifsp.sdm.mybooks.adapter.BookAdapter
import br.edu.scl.ifsp.sdm.mybooks.data.Book
import br.edu.scl.ifsp.sdm.mybooks.viewmodel.BookViewModel

class ListBooksFragment : Fragment() {
    private var _binding: FragmentListBooksBinding? = null
    private val binding get() = _binding!!
    lateinit var bookAdapter: BookAdapter
    lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBooksBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupClick()
        setupAdapter()
        return root
    }

    private fun setupClick() {
        binding.addNewBook.setOnClickListener {
            findNavController().navigate(R.id.action_listBooksFragment_to_registerFragment)
        }
    }

    private fun setupAdapter() {
        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        viewModel.allBooks.observe(viewLifecycleOwner) { list ->
            list?.let {
                bookAdapter.updateList(list as ArrayList<Book>)
            }
        }
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter()
        recyclerView.adapter = bookAdapter

        val listener = object : BookAdapter.BookListener {
            override fun onItemClick(pos: Int) {
                val c = bookAdapter.booksListFilterable[pos]
                val bundle = Bundle()
                bundle.putInt("idBook", c.id)
                findNavController().navigate(
                    R.id.action_listBooksFragment_to_detailsFragment,
                    bundle
                )
            }
        }
        bookAdapter.setClickListener(listener)
    }
}
