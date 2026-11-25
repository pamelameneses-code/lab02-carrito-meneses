package com.meneses.avisoscampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.meneses.avisoscampus.data.AvisosRepositoryMemoria

object AvisosViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        modelClass.cast(AvisosViewModel(AvisosRepositoryMemoria()))!!
}
