package com.example.ktsample.utils

import kotlin.random.Random


fun genRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars[Random.nextInt(0, allowedChars.size)] }
        .joinToString("")
}

