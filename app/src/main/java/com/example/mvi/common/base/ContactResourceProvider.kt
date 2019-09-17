package com.example.mvi.common.base

interface ContactResourceProvider {
    fun provideErrorTitle(): String
    fun provideNormalTitle(): String
    fun provideLoadingTitle(): String
}
