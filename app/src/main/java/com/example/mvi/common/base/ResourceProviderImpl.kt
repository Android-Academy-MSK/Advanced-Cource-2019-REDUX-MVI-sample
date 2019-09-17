package com.example.mvi.common.base

import android.content.Context
import com.example.mvi.R

class ResourceProviderImpl(private val applicationContext: Context) : ContactResourceProvider {


    override fun provideErrorTitle() = applicationContext.getString(R.string.contacts_toolbar_title_error)

    override fun provideNormalTitle() = applicationContext.getString(R.string.contacts_toolbar_title)

    override fun provideLoadingTitle() = applicationContext.getString(R.string.contacts_toolbar_title_loading)

}