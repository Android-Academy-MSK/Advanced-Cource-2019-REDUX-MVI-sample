package com.example.mvi.contacts.presentation.mvi

import com.example.mvi.common.base.ContactResourceProvider
import com.example.mvi.common.base.Reducer
import com.example.mvi.contacts.ContactsAction
import com.example.mvi.contacts.ContactsViewState


class ContactsReducer(private val provider: ContactResourceProvider) : Reducer<ContactsAction, ContactsViewState> {

    override fun reduce(viewState: ContactsViewState, action: ContactsAction): ContactsViewState {

        return when (action) {
            is ContactsAction.Search -> viewState
            is ContactsAction.ContactsLoaded -> {
                ContactsViewState.createContactsSuccessfullyReceived(
                    toolbarTitle = provider.provideNormalTitle(),
                    items = action.items
                )
            }
            is ContactsAction.Loading -> ContactsViewState.createLoadingState(
                toolbarTitle = provider.provideLoadingTitle()
            )

            is ContactsAction.ContactsDontFound -> ContactsViewState.createErrorNoContactsWereFound(
                toolbarTitle = provider.provideNormalTitle()
            )
            is ContactsAction.ContactsError -> viewState
            is ContactsAction.ForceUpdate -> viewState
        }
    }

}