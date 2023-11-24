package com.latihan.jetsubmission.di

import com.latihan.jetsubmission.data.TokoRepository

object Injection {
    fun provideRepository(): TokoRepository {
        return TokoRepository.getInstance()
    }
}