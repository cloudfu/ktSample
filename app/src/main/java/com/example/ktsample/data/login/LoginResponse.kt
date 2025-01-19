package com.example.ktsample.data.login

data class LoginResponse(
    val uId: String,
    val firstName: String,
    val lastName: String,
    val address: String
){
    override fun toString(): String{
        return "uId:$uId firstName$firstName"
    }
}