package com.example.ktsample.ui.component.list

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import com.example.ktsample.R
import com.example.ktsample.databinding.ActivityRecyclerViewBinding
import com.example.ktsample.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecyclerViewActivity @Inject constructor(): BindingActivity<ActivityRecyclerViewBinding>(R.layout.activity_recycler_view) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.init()
    }

    private val mListPokemonViewModel: ListPokemonViewModel by viewModels()

    private fun init(){
        binding{
            mBinding.button.setOnClickListener{
                mListPokemonViewModel.fetchNextPokemonList()
            }
        }
    }

}