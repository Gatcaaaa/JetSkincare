package com.latihan.jetsubmission.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.jetsubmission.data.TokoRepository
import com.latihan.jetsubmission.data.UiState
import com.latihan.jetsubmission.model.OrderSkincare
import com.latihan.jetsubmission.model.Skincare
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: TokoRepository):ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderSkincare>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderSkincare>>
        get() = _uiState

    fun getSkincareById(skincareId: Long){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderSkincareById(skincareId))
        }
    }

    fun addToCart(skincare: Skincare, count: Int){
        viewModelScope.launch {
            repository.updateOrderSkincare(skincare.id, count)
        }
    }
}