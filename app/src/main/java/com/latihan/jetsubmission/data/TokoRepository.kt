package com.latihan.jetsubmission.data

import com.latihan.jetsubmission.model.OrderSkincare
import com.latihan.jetsubmission.model.SkincareDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class TokoRepository {
    private val orderSkincare = mutableListOf<OrderSkincare>()

    init {
        if (orderSkincare.isEmpty()){
            SkincareDataSource.dummySkincare.forEach {
                orderSkincare.add(OrderSkincare(it,0))
            }
        }
    }
    fun getAllSkincare(): Flow<List<OrderSkincare>>{
        return flowOf(orderSkincare)
    }
    fun getOrderSkincareById(skincareId: Long): OrderSkincare{
        return orderSkincare.first {
            it.item.id == skincareId
        }
    }
    fun updateOrderSkincare(skincareId: Long, newCountValue: Int): Flow<Boolean>{
        val index = orderSkincare.indexOfFirst { it.item.id == skincareId }
        val result = if (index >= 0 ){
            val ordersSkincare = orderSkincare[index]
           orderSkincare[index] =
               ordersSkincare.copy(item = ordersSkincare.item, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderSkincare(): Flow<List<OrderSkincare>>{
        return getAllSkincare()
            .map { orderSkincares ->
                orderSkincares.filter { orderSkincare ->
                    orderSkincare.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance : TokoRepository? = null
        fun getInstance(): TokoRepository =
           instance ?: synchronized(this){
               TokoRepository().apply {
                   instance = this
               }
           }
    }
}