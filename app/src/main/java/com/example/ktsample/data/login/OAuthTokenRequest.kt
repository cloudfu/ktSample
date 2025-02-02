package com.example.ktsample.data.login

data class OAuthTokenRequest(
    var clientId: String,
    var clientSecret: String,
    var code: String,
    var redirectUri: String


)