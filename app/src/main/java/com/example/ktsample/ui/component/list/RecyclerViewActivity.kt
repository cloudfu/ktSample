package com.example.ktsample.ui.component.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.ktsample.R
import com.example.ktsample.databinding.ActivityRecyclerviewBinding
import com.example.ktsample.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecyclerViewActivity @Inject constructor(): BindingActivity<ActivityRecyclerviewBinding>(R.layout.activity_recyclerview) {

    private val vm: ListPokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.init()
    }

    private val mListPokemonViewModel: ListPokemonViewModel by viewModels()

    private fun init(){
        binding{
            viewModel = vm
            listAdapter = PokemonListAdapter()
//            button.setOnClickListener{
//                mListPokemonViewModel.fetchNextPokemonList()
//            }
        }

        this.vm.pokemonList.observe(this, Observer {
            binding{
                val adapter = PokemonListAdapter()
                recyclerView.adapter = adapter
                adapter.submitList(it)
            }
        })
    }
}