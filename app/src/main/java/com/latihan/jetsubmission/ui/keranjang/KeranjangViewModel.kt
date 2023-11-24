package com.latihan.jetsubmission.ui.keranjang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.jetsubmission.data.TokoRepository
import com.latihan.jetsubmission.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class KeranjangViewModel(private val repository: TokoRepository):ViewModel() {
    private val _uiState: MutableStateFlow<UiState<State>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<State>>
        get() = _uiState

    fun getAddedOrderSkincare(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderSkincare()
                .collect{ orderSkincare ->
                    val totalPrice = orderSkincare.sumOf { it.item.price * it.count }
                    _uiState.value = UiState.Success(State(orderSkincare, totalPrice))
                }
        }
    }

    fun updateOrderSkincare(skincareId: Long, count: Int){
        viewModelScope.launch {
            repository.updateOrderSkincare(skincareId, count)
                .collect{isUpdated ->
                    if (isUpdated){
                        getAddedOrderSkincare()
                    }
                }
        }
    }
}