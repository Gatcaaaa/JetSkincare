package com.latihan.jetsubmission.ui.keranjang

import com.latihan.jetsubmission.model.OrderSkincare

data class State(
    val orderSkincare: List<OrderSkincare>,
    val totalPrice: Int
)
