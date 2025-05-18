package com.piyush.moviedbtask.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.piyush.moviedbtask.databinding.FragmentBookmarkBinding
import com.piyush.moviedbtask.presentation.ui.adapters.CommonAdapter
import com.piyush.moviedbtask.presentation.ui.adapters.MovieAdapter
import com.piyush.moviedbtask.presentation.ui.viewmodels.BookmarkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookmarkViewModel by viewModel()

    private lateinit var bookmarkAdapter: CommonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bookmarkAdapter = CommonAdapter { movie ->
            val action = BookmarkFragmentDirections.actionNavigationBookmarkToMovieDetailFragment(movie)
            findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = bookmarkAdapter

        observeBookmarkedMovies()
    }

    private fun observeBookmarkedMovies() {
        viewModel.bookmarkedMovies.observe(viewLifecycleOwner) { movies ->
            bookmarkAdapter.submitList(movies)
            binding.emptyView.isVisible = movies.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
