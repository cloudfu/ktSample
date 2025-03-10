package com.example.ktsample.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ktsample.data.pokemon.PokemonEntity

@Dao
interface PokemonDao {
    @Insert
    suspend fun insertPokemon(pokemon: PokemonEntity): Long

    @Query("SELECT * FROM PokemonList")
    suspend fun getAllPokemon(): List<PokemonEntity>

    @Query("SELECT * FROM PokemonList WHERE id = :userId")
    suspend fun getPokemonById(userId: Int): PokemonEntity?
}