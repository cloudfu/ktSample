package com.example.ktsample.ui.component.pokemon

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.ktsample.R
import com.example.ktsample.data.pokemon.Pokemon
import com.example.ktsample.databinding.ViewholderPokemonItemBinding
import com.example.ktsample.ui.adatper.BindingListAdapter
import com.example.ktsample.ui.extension.binding

class PokemonListAdapter: BindingListAdapter<Pokemon, PokemonListAdapter.PokemonViewHolder>(diffUtil){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return parent.binding<ViewholderPokemonItemBinding>(R.layout.viewholder_pokemon_item).let(::PokemonViewHolder)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        holder.bindPokemon(getItem(position))

    inner class PokemonViewHolder constructor(
        private val binding: ViewholderPokemonItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != NO_POSITION }
                    ?: return@setOnClickListener
//                DetailActivity.startActivity(binding.transformationLayout, getItem(position))
            }
        }

        fun bindPokemon(pokemon: Pokemon) {
            binding.pokemon = pokemon
            binding.executePendingBindings()
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem == newItem
        }
    }
}