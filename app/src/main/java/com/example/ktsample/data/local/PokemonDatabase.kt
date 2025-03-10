package com.example.ktsample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ktsample.data.pokemon.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}