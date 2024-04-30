package com.example.mviandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mviandroid.ui.theme.MVIAndroidTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<PostViewModel>()
        viewModel.getPosts()
        setContent {
            val context = LocalContext.current

            MVIAndroidTheme {
                //collectAsState() :collects values from a Flow and transforms it into Compose State
                val state by viewModel.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp,
                            ),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(state.post) { post ->
                            Column(Modifier.padding(horizontal = 8.dp,
                                vertical = 4.dp)) {
                                Text(
                                    text = post.title,
                                    style = MaterialTheme.typography.h6.copy(fontSize = 18.sp)
                                )
                                Text(
                                    text = post.title,
                                    style = MaterialTheme.typography.body1.copy(fontSize = 10.sp)
                                )

                            }
                        }
                    }

                    if (state.progressBar) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()

                        }
                    }
                    viewModel.collectSideEffect { sideEffect: UIComponent ->
                        when (sideEffect) {
                            is UIComponent.Toast ->
                                Toast.makeText(context,  sideEffect.text, Toast.LENGTH_LONG).show()

                            is UIComponent.Dialog -> TODO()
                            is UIComponent.SnackBar -> TODO()
                        }

                    }
                }
            }
        }
    }
}
