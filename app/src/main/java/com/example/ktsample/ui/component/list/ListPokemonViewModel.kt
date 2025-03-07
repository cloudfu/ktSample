package com.example.ktsample.ui.component.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsample.data.pokemon.Pokemon
import com.example.ktsample.data.repository.DataRepository
import com.example.ktsample.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(private val dataRepository: DataRepository) : BaseViewModel(){

    var isLoading: Boolean = false
        private set
    var toastMessage: String = ""
        private set
    private val pokemonPageIndex: MutableStateFlow<Int> = MutableStateFlow(0)

//    private val pokemonListFlow = pokemonPageIndex.flatMapLatest { pageIndex ->
//        dataRepository.fetchPokemonList(
//            page = pageIndex,
//            onStart = {isLoading = true},
//            onSuccess = {isLoading = false},
//            onComplete = {isLoading = false},
//            onError = { errMsg ->
//                toastMessage = errMsg
//            }
//        )
//    }
//
//    val pokemonList: List<Pokemon> by pokemonListFlow.bindProperty(viewModelScope, emptyList())
    var pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()

    init{
        viewModelScope.launch {
            pokemonPageIndex.flatMapLatest { pageIndex ->
                dataRepository.fetchPokemonList(
                    page = pageIndex,
                    onStart = { isLoading = true },
                    onSuccess = { isLoading = false },
                    onComplete = { isLoading = false },
                    onError = { errMsg ->
                        toastMessage = errMsg
                    }
                )
//                var list = result.single()
//                println(list)
            }.collect{
                pokemonList.value = it
                println(it.size)
            }
        }
    }

    fun fetchNextPokemonList(){
        if (!isLoading) {
            pokemonPageIndex.value++
            println(pokemonList.value?.size)
        }
    }

    /***
     * 请求下一页，翻页
     */
    fun fetchNextPokemonList1(){
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