package com.example.mviandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviandroid.network.model.PostApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
//     val context = LocalContext.current for calling this method we need to implement this interface
class PostViewModel : ViewModel(), ContainerHost<PostState, UIComponent> {

//in the GetPost usecase we need to pass httpclient
    private val getPost = GetPost(PostApi.providePostAPI())
//container of MVI with PostState
    override val container: Container<PostState, UIComponent> = container(PostState())
    fun getPosts() {
        intent {
            val post = getPost.execute()
            post.onEach { dataState ->
                when (dataState) {
                    is DataState.isLoading -> {
                        reduce { state.copy(progressBar = dataState.isLoading) }
                    }

                    is DataState.Success -> {
                        reduce { state.copy(post = dataState.data) }
                    }

                    is DataState.Error -> {
                        when (dataState.uiComponent) {
                            is UIComponent.Toast -> {
                               postSideEffect(UIComponent.Toast(dataState.uiComponent.text))

                            }

                          else -> {}
                        }
                    }
                }

            }.launchIn(viewModelScope)

        }
    }

}