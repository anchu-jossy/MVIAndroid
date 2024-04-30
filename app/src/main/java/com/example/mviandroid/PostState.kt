package com.example.mviandroid

import com.example.mviandroid.network.model.Post

data class PostState(
    val progressBar: Boolean = false,
    val post: List<Post> = emptyList(),

)

