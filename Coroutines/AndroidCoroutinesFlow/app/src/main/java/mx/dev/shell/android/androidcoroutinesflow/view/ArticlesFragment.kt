package mx.dev.shell.android.androidcoroutinesflow.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.dev.shell.android.androidcoroutinesflow.R
import mx.dev.shell.android.androidcoroutinesflow.databinding.FragmentArticlesBinding
import mx.dev.shell.android.androidcoroutinesflow.vm.ArticlesViewModel

class ArticlesFragment : Fragment() {

    private lateinit var binding: FragmentArticlesBinding
    private lateinit var viewModel: ArticlesViewModel

    private val articlesAdapter = ArticlesAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        observeViewModel()

        setupView()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.articles.observe(this as LifecycleOwner) { article ->
            binding.articlesProgress.visibility = View.GONE
            binding.articlesRecycler.visibility = View.VISIBLE
            articlesAdapter.onAddNewsItem(article)
            binding.articlesRecycler.smoothScrollToPosition(0)
        }
    }

    private fun setupView() {
        binding.articlesRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = articlesAdapter
        }
    }
}