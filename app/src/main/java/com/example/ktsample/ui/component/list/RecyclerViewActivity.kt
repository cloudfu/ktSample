package com.example.ktsample.ui.component.list

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ktsample.R
import com.example.ktsample.databinding.ActivityRecyclerViewBinding
import com.example.ktsample.ui.base.BindingActivity
import com.example.ktsample.ui.component.login.LoginViewModel

class RecyclerViewActivity : BindingActivity<ActivityRecyclerViewBinding>(R.layout.activity_recycler_view) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.init()
    }

    private val mListPokemonViewModel: ListPokemonViewModel by viewModels()
    private fun init(){
        mListPokemonViewModel.fetchNextPokemonList()
    }
}