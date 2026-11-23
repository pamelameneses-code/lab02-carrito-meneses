package com.meneses.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.meneses.murocampus.data.PostRepositoryRemote
import com.meneses.murocampus.data.remote.RetrofitClient
import com.meneses.murocampus.ui.PantallaLista
import com.meneses.murocampus.ui.theme.MuroCampusTheme
import com.meneses.murocampus.viewmodel.PostsViewModel
import com.meneses.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val factory = PostsViewModelFactory(PostRepositoryRemote(RetrofitClient.api))
        val vm = ViewModelProvider(this, factory)[PostsViewModel::class.java]
        setContent {
            MuroCampusTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("MuroCampus") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                        )
                    },
                ) { padding ->
                    PantallaLista(vm, Modifier.fillMaxSize().padding(padding))
                }
            }
        }
    }
}
