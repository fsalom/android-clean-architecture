package es.rudo.androidbaseproject.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.rudo.androidbaseproject.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        setupToolbar()
        initObservers()
        viewModel.loadCharacters()
        initAdapter()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initObservers() {
        viewModel.characters.observe(this) {
            (binding.characterList.adapter as? CharactersAdapter)?.submitList(it)
        }
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
        // TODO add detail navigation
    }
}
