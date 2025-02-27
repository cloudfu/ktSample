package com.example.ktsample.ui.component.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsample.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel(){

    var isLoading: Boolean = false
        private set
    var toastMessage: String = ""
        private set
    private val pokemonPageIndex: MutableStateFlow<Int> = MutableStateFlow<Int>(0)

    private val monsterListFlow = pokemonPageIndex.transformLatest { pageIndex ->
        emit(dataRepository.fetchPokemonList(
            page = pageIndex,
            onStart = {isLoading = true},
            onSuccess = {isLoading = false},
            onComplete = {isLoading = false},
            onError = { errMsg ->
                toastMessage = errMsg
            }
        ))
    }

    /***
     * 请求下一页，翻页
     */
    fun fetchNextPokemonList(){
        if(!isLoading){
            viewModelScope.launch {
                dataRepository.fetchPokemonList(
                    page = 0,
                    onStart = {isLoading = true},
                    onSuccess = {isLoading = false},
                    onComplete = {isLoading = false},
                    onError = { errMsg ->
                        toastMessage = errMsg
                    }
                ).collect{
                    var pokemonList = it;
                    var count = pokemonList.size
                    Log.i("count", count.toString())
                }
            }
        }
    }
}