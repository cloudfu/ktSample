package com.example.ktsample.data.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PokemonList")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)