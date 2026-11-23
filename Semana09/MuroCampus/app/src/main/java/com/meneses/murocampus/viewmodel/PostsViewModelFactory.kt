package com.meneses.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meneses.murocampus.data.PostRepository

class PostsViewModelFactory(private val repo: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == PostsViewModel::class.java)
        @Suppress("UNCHECKED_CAST")
        return PostsViewModel(repo) as T
    }
}
