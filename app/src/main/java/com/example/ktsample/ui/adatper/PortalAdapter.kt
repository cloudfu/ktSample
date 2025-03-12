package com.example.ktsample.ui.adatper

import com.example.ktsample.ui.component.list.BindingListAdapter
import com.example.ktsample.ui.component.list.PokemonListAdapter
import com.example.ktsample.ui.component.list.binding

package com.example.ktsample.ui.component.list

import android.os.SystemClock
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.ktsample.R
import com.example.ktsample.data.pokemon.Pokemon
import com.example.ktsample.databinding.ViewholderPokemonItemBinding

class PortalAdapter: BindingListAdapter<Pokemon, PortalAdapter.PortalViewHolder>(diffUtil){

    private var onClickedAt = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return parent.binding<ViewholderPokemonItemBinding>(R.layout.viewholder_pokemon_item).let(::PokemonViewHolder)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        holder.bindPokemon(getItem(position))

    inner class PortalViewHolder (
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