package com.piyush.moviedbtask.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.piyush.moviedbtask.databinding.FragmentSearchBinding
import com.piyush.moviedbtask.presentation.ui.adapters.CommonAdapter
import com.piyush.moviedbtask.presentation.ui.adapters.MovieAdapter
import com.piyush.moviedbtask.presentation.ui.viewmodels.SearchViewModel
import com.piyush.moviedbtask.utils.Resource
import com.piyush.moviedbtask.utils.textChangesFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var searchAdapter: CommonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchAdapter = CommonAdapter { movie ->
            val action = SearchFragmentDirections.actionNavigationSearchToMovieDetailFragment(movie)
            findNavController().navigate(action)
        }

        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
            setHasFixedSize(true)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            binding.etSearch.textChangesFlow()
                .map { it.toString() }
                .collectLatest { query ->
                    searchViewModel.search(query)
                }
        }

        lifecycleScope.launch {
            searchViewModel.searchResults.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        searchAdapter.submitList(resource.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Search error: ${resource.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}