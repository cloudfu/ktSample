package com.example.ktsample.ui.component.pokemon

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.ktsample.R
import com.example.ktsample.databinding.ActivityPokemonListBinding
import com.example.ktsample.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokemonListActivity @Inject constructor(): BindingActivity<ActivityPokemonListBinding>(R.layout.activity_pokemon_list) {

    private val mViewModel: ListPokemonViewModel by viewModels()
    private var mPokemonAdapter: PokemonListAdapter = PokemonListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.init()
    }

    private fun init(){
        binding{
            viewModel = mViewModel
            recyclerView.adapter = mPokemonAdapter
        }

        this.mViewModel.pokemonList.observe(this) {
            mPokemonAdapter.submitList(it)
        }
    }
}