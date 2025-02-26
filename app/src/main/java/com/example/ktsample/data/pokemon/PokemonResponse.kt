package com.example.ktsample.data.pokemon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
//data class PokemonResponse(
//    @field:Json(name = "count") val count: Int,
//    @field:Json(name = "next") val next: String?,
//    @field:Json(name = "previous") val previous: String?,
//    @field:Json(name = "results") val results: List<Pokemon>,
//)

data class PokemonResponse(
    var count: Int  = 0,
    var next: String = "",
    var previous: String = "",
    var results: List<Pokemon> = emptyList(),
)