package com.latihan.jetsubmission.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.latihan.jetsubmission.data.TokoRepository
import com.latihan.jetsubmission.ui.detail.DetailViewModel
import com.latihan.jetsubmission.ui.home.HomeViewModel
import com.latihan.jetsubmission.ui.keranjang.KeranjangViewModel

class ViewModelFactory(private  val repository: TokoRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(KeranjangViewModel::class.java)){
            return KeranjangViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class: "+ modelClass.name)
    }
}