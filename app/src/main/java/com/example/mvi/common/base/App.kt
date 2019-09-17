package com.example.mvi.common.base

import android.app.Application
import com.example.mvi.contacts.ContactsAction
import com.example.mvi.contacts.ContactsViewState
import com.example.mvi.contacts.data.FakeDataProvider
import com.example.mvi.contacts.domain.ContactSearchRepository
import com.example.mvi.contacts.domain.ContactsMiddleWare
import com.example.mvi.contacts.presentation.mvi.ContactsReducer
import com.example.mvi.contacts.presentation.mvi.ContactsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    private val appModule = module {

        single<ContactSearchRepository> { FakeDataProvider() }
        single<ContactResourceProvider> { ResourceProviderImpl(applicationContext = get()) }

        single<Reducer<ContactsAction, ContactsViewState>> {
            ContactsReducer(
                provider = get()
            )
        }
        single<Store<ContactsAction, ContactsViewState>> {
            DefaultStore(
                reducer = get(),
                middleWare = get(),
                initialState = get()
            )
        }

        single { ContactsViewState.creatEmpty() }

        single<MiddleWare<ContactsAction, ContactsViewState>> {
            ContactsMiddleWare(repository = get())
        }

        viewModel { ContactsViewModel(store = get()) }
    }
}