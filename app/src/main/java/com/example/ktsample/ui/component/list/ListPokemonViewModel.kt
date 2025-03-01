package com.example.ktsample.ui.component.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsample.data.pokemon.Pokemon
import com.example.ktsample.data.repository.DataRepository
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
class ListPokemonViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel(){

    var isLoading: Boolean = false
        private set
    var toastMessage: String = ""
        private set
    private val pokemonPageIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    private var pokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
//    @OptIn(ExperimentalCoroutinesApi::class)
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

    init{
        viewModelScope.launch {
            pokemonPageIndex.map { pageIndex ->
                val result = dataRepository.fetchPokemonList(
                    page = pageIndex,
                    onStart = { isLoading = true },
                    onSuccess = { isLoading = false },
                    onComplete = { isLoading = false },
                    onError = { errMsg ->
                        toastMessage = errMsg
                    }
                ).collect{
                    println(it.size)
                }
//                var list = result.single()
//                println(list)
            }.collect()
        }
    }

    fun fetchNextPokemonLst(){
        if (!isLoading) {
            pokemonPageIndex.value++
        }
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