package com.example.ktsample.data.login

data class OAuthCodeRequest(
    var client_id: String,
    var redirect_uri: String,
    var scope: String,
    var state: String
)