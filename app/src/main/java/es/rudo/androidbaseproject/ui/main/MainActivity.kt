package es.rudo.androidbaseproject.ui.main

import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import es.rudo.androidbaseproject.R
import es.rudo.androidbaseproject.databinding.ActivityMainBinding
import es.rudo.androidbaseproject.helpers.Constants
import es.rudo.androidbaseproject.helpers.extensions.isNetworkAvailable
import es.rudo.androidbaseproject.helpers.extensions.onePlusOne
import es.rudo.androidbaseproject.helpers.extensions.showSimpleDialog
import es.rudo.androidbaseproject.ui.base.BaseActivity
import es.rudo.androidbaseproject.ui.detail.DetailActivity
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {

    override fun setUpViews() {
        setupToolbar()
        initObservers()
        initListeners()

        isNetworkAvailable
        viewModel.loadCharacters(isNetworkAvailable)
        initAdapter()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initObservers() {
        viewModel.uiState
            .map { it.isLoading }
            .distinctUntilChanged()
            .collectOnLifecycle {
                binding.characterList.isVisible = !it
                showShimmerEffect(it)
            }

        viewModel.uiState
            .map { it.items }
            .distinctUntilChanged()
            .collectOnLifecycle {
                if (it.isNotEmpty())
                    (binding.characterList.adapter as? CharactersAdapter)?.submitList(it)
            }

        viewModel.uiState
            .map { it.error }
            .collectOnLifecycle {
                handleError(it)
            }
    }

    private fun initListeners() {
        binding.buttonRefresh.setOnClickListener {
            viewModel.loadCharacters(isNetworkAvailable)
        }

        binding.characterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val listSize = (recyclerView.adapter as CharactersAdapter).currentList.size

                if (layoutManager.findLastVisibleItemPosition() >= listSize - 2)
                    viewModel.notifyLastVisible()
            }
        })
    }

    private fun initAdapter() {
        val adapter = CharactersAdapter { clickedCharacter ->
            goToDetail(clickedCharacter.id)
        }
        adapter.setHasStableIds(true)
        binding.characterList.layoutManager = LinearLayoutManager(this)
        binding.characterList.adapter = adapter
    }

    private fun goToDetail(id: Int) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.INTENT_ID, id)
        })
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) {
            binding.characterList.visibility = View.GONE
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.shimmerViewContainer.startShimmer()
        } else {
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
            binding.characterList.visibility = View.VISIBLE
        }
    }
}
