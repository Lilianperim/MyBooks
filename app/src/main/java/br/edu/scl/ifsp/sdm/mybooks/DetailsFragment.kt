package br.edu.scl.ifsp.sdm.mybooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.scl.ifsp.sdm.mybooks.data.Book
import br.edu.scl.ifsp.sdm.mybooks.databinding.FragmentDetailsBinding
import br.edu.scl.ifsp.sdm.mybooks.viewmodel.BookViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    lateinit var book: Book
    lateinit var titleEditText: EditText
    lateinit var genreEditText: EditText
    lateinit var authorEditText: EditText
    lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleEditText = binding.commonLayout.editTextTitulo
        genreEditText = binding.commonLayout.editTextGenero
        authorEditText = binding.commonLayout.editTextAutor

        val idBook = requireArguments().getInt("idBook")
        viewModel.getBookById(idBook)
        viewModel.book.observe(viewLifecycleOwner) { result ->
            result?.let {
                book = result
                titleEditText.setText(book.title)
                genreEditText.setText(book.genre)
                authorEditText.setText(book.author)
            }
        }

        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.details_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_changeBook -> {

                            book.title = titleEditText.text.toString()
                            book.genre = genreEditText.text.toString()
                            book.author = authorEditText.text.toString()

                            viewModel.update(book)

                            Snackbar.make(binding.root, "Livro alterado", Snackbar.LENGTH_SHORT)
                                .show()

                            findNavController().popBackStack()
                            true
                        }

                        R.id.action_deleteBook -> {
                            viewModel.delete(book)

                            Snackbar.make(binding.root, "Livro deletado", Snackbar.LENGTH_SHORT)
                                .show()
                            findNavController().popBackStack()
                            true
                        }

                        else -> false
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }
}

