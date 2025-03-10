package com.example.ktsample.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: PokemonDatabase? = null;

    fun getDatabase(context: Context): PokemonDatabase{
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PokemonDatabase::class.java,
                "database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}