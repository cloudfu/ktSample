package com.example.ktsample.ui.adatper

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.ktsample.data.pokemon.Pokemon
import timber.log.Timber

class PortalAdapter: BindingListAdapter<String, PortalAdapter.ItemViewHolder>(diffUtil){

    private var onClickedAt = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = android.view.LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ItemViewHolder(view)
//        return parent.binding<ViewholderPokemonItemBinding>(android.R.layout.simple_list_item_1).let(::PortalViewHolder)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnClickListener {
                Log.i("item", textView.text.toString())
            }
        }

        fun bind(name: String) {
            textView.text = name
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}