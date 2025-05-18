package com.piyush.moviedbtask.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.piyush.moviedbtask.R
import com.piyush.moviedbtask.databinding.FragmentMovieDetailBinding
import com.piyush.moviedbtask.presentation.ui.viewmodels.MovieDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailFragmentArgs by navArgs()
    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadMovie(args.movie.id)

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            updateBookmarkIcon(movie.isBookmarked)

            binding.apply {
                tvTitle.text = "Original: ${movie.original_title}"
                tvOverview.text = movie.overview
                tvReleaseDate.text = "Release Date: ${movie.release_date}"
                rating.text = "Rating: ${movie.vote_average} (${movie.vote_count} votes)"
                language.text = "Language: ${movie.original_language.uppercase()}"
                tvAdult.text = if (movie.adult) "Adult: Yes" else "Adult: No"
                popularity.text = "Popularity: ${movie.popularity}"

                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500${movie.backdrop_path}")
                    .into(imgBackdrop)
            }
        }

        binding.imgBookmark.setOnClickListener {
            viewModel.toggleBookmark()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateBookmarkIcon(isBookmarked: Int) {
        val iconRes = if (isBookmarked == 1) R.drawable.ic_bookmarked else R.drawable.icon_bookmark
        binding.imgBookmark.setImageResource(iconRes)
    }
}
