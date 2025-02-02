package com.example.ktsample.data.login

data class OAuthTokenResponse(
    var accessToken: String,
    var scope: String,
    var tokenType: String
){
    override fun toString(): String{
        return "accessToken:$accessToken, scope:$scope, tokenType:$tokenType"
    }
}