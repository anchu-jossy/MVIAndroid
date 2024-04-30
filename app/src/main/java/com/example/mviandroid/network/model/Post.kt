package com.example.mviandroid.network.model

import kotlinx.serialization.Serializable
//data class will convert json object to Kotlin object
@Serializable
data class Post(
    val userId: Int, val id: Int, val title: String, val body: String
)
