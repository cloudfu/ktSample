package com.example.ktsample.data.city

data class City(
    val id: String,
    val name: String){

    override fun toString(): String {
        return "id:$id, name=$name"
    }
}