package es.rudo.androidbaseproject.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import es.rudo.androidbaseproject.R
import es.rudo.androidbaseproject.databinding.ActivityDetailBinding
import es.rudo.androidbaseproject.ui.base.BaseActivity
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : BaseActivity<DetailViewModel, ActivityDetailBinding>(R.layout.activity_detail) {

    override fun setUpViews() {
        setupToolbar()
        initObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState
                .map { it.isLoading }
                .distinctUntilChanged()
                .collect {
                    binding.progressCircular.isVisible = it
                    binding.container.isVisible = !it
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .map { it.character }
                .distinctUntilChanged()
                .collect {
                    with(binding) {
                        toolbarTitle.text = it.name
                        textName.text = getString(R.string.detail_name, it.name)
                        textLocation.text = getString(R.string.detail_location, it.location.name)
                        textSpecies.text = getString(R.string.detail_species, it.species)
                        textStatus.text = getString(R.string.detail_status, it.status)

                        Glide.with(this@DetailActivity)
                            .load(it.image)
                            .placeholder(R.drawable.image_placeholder)
                            .into(imageCharacter)
                    }
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .map { it.error }
                .collect {
                    handleError(it)
                }
        }
    }

    companion object {
        const val INTENT_ID = "ID"
    }
}