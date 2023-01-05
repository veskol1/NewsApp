package com.example.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.ArticlesAdapter
import com.example.newsapp.databinding.FragmentMainBinding
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), ArticlesAdapter.ArticleClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: NewsViewModel by activityViewModels()
    private val adapter = ArticlesAdapter(articles = arrayListOf(), this@MainFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

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
                            uiState.articles?.let { updateAdapterList(it) }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        newsViewModel.checkForUpdate()
    }

    private fun updateAdapterList(articles: ArrayList<Article>) {
        adapter.updateList(articles)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onArticlesClicked(position: Int) {
        val article = newsViewModel.getArticle(pos = position)
        article?.let {
            val action = MainFragmentDirections.actionMainFragmentToArticaleFragment(it)
            findNavController().navigate(action)
        }
    }
}