package com.latihan.jetsubmission.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latihan.jetsubmission.data.TokoRepository
import com.latihan.jetsubmission.data.UiState
import com.latihan.jetsubmission.model.OrderSkincare
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TokoRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderSkincare>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderSkincare>>>
        get() = _uiState

    fun getAllSkincare(){
        viewModelScope.launch {
            repository.getAllSkincare()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{ orderSkincares ->
                    _uiState.value = UiState.Success(orderSkincares)
                }
        }
    }
}