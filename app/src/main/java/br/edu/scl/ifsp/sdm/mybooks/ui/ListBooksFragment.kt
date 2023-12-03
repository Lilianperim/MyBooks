package br.edu.scl.ifsp.sdm.mybooks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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
    private lateinit var viewModel: BookViewModel

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
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        bookAdapter.filter.filter(p0)
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
