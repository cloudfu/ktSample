package com.example.ktsample.ui.adatper

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import com.example.ktsample.data.Const.POKEMON_PORTAL
import com.example.ktsample.data.Const.OAUTH_CLIENT_ID
import com.example.ktsample.data.Const.OAUTH_CLIENT_SECRET
import com.example.ktsample.data.Const.OAUTH_REDIRECT_URI
import com.example.ktsample.data.Const.PORTAL_LIST
import com.example.ktsample.ui.component.pokemon.PokemonListActivity

class PortalAdapter: BindingListAdapter<String, PortalAdapter.ItemViewHolder>(diffUtil){

    private var onClickedAt = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = android.view.LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.tag = item
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnClickListener {
                Timber.i(textView.text.toString())
                val intent = when(it.tag.toString()){
                    POKEMON_PORTAL -> Intent(itemView.context, PokemonListActivity::class.java)
                    else -> return@setOnClickListener
                }
                itemView.context.startActivity(intent)
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