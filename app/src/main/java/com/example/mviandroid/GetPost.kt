package com.example.mviandroid

import com.example.mviandroid.network.model.Post
import com.example.mviandroid.network.model.PostApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
// usecase class to get post
class GetPost(private val postApi: PostApi) {
    fun execute(): Flow<DataState<List<Post>>> {
        return flow {
            emit(DataState.isLoading(true))

            try {
                val post = postApi.getPosts()
                emit(DataState.Success(post))
            } catch (e: Exception) {
                emit(DataState.Error(UIComponent.Toast(e.message.toString())))
            } finally {
                emit(DataState.isLoading(false))
            }
        }

    }
}