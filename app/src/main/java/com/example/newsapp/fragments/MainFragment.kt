package com.example.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.newsapp.databinding.FragmentMainBinding
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.Status
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsViewModel.uiState.collect{ uiState ->
                    when(uiState.status) {
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.errorView.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorView.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                        Status.DONE -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorView.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            uiState.articles?.let { configureRecyclerView(it) }
                        }
                    }
                }
            }
        }
    }

    private fun configureRecyclerView(articles: ArrayList<Article>) {
        //to do
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}