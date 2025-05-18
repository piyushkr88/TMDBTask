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
import com.piyush.moviedbtask.databinding.FragmentHomeBinding
import com.piyush.moviedbtask.presentation.ui.adapters.MovieAdapter
import com.piyush.moviedbtask.presentation.ui.viewmodels.HomeViewModel
import com.piyush.moviedbtask.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var trendingAdapter: MovieAdapter
    private lateinit var nowPlayingAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        trendingAdapter = MovieAdapter { movie ->
            val action = HomeFragmentDirections.actionNavigationHomeToMovieDetailFragment(movie)
            findNavController().navigate(action)
        }

        nowPlayingAdapter = MovieAdapter { movie ->
            val action = HomeFragmentDirections.actionNavigationHomeToMovieDetailFragment(movie)
            findNavController().navigate(action)
        }
        binding.rvTrendingMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
            setHasFixedSize(true)
        }

        binding.rvNowPlayingMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = nowPlayingAdapter
            setHasFixedSize(true)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.trendingMovies.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loadingProgress.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val data = resource.data
                        trendingAdapter.submitList(data)
                        binding.loadingProgress.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.loadingProgress.visibility = View.GONE
                        Toast.makeText(requireContext(), "Trending Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.nowPlayingMovies.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loadingProgress.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val data = resource.data
                        nowPlayingAdapter.submitList(data)
                        binding.loadingProgress.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.loadingProgress.visibility = View.GONE
                        Toast.makeText(requireContext(), "Now Playing Error: ${resource.message}", Toast.LENGTH_SHORT).show()
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

