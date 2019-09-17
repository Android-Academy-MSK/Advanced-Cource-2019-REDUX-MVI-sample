package com.example.mvi.contacts

import com.example.mvi.common.base.Action
import com.example.mvi.common.base.ViewState
import com.example.mvi.contacts.domain.response.Contact


sealed class ContactsAction : Action {
    //ui actions
    data class Search(val search: String) : ContactsAction()

    //data actions
    object Loading : ContactsAction()

    object ForceUpdate : ContactsAction()
    data class ContactsLoaded(val items: List<Contact>) : ContactsAction()
    data class ContactsError(val throwable: Throwable?) : ContactsAction()
    object ContactsDontFound : ContactsAction()
}

data class ContactsViewState(
    val items: List<Contact>,
    val toolbarTitle: String,
    val loading: Boolean,
    val error: ContactError
) : ViewState {


    companion object {
        fun creatEmpty() =
            ContactsViewState(
                items = emptyList(),
                toolbarTitle = "",
                loading = false,
                error = ContactError.NO_ERROR
            )

        fun createLoadingState(toolbarTitle: String) =
            ContactsViewState(
                items = emptyList(),
                toolbarTitle = toolbarTitle,
                loading = true,
                error = ContactError.NO_ERROR
            )

        fun createErrorNoContactsWereFound(toolbarTitle: String) =
            ContactsViewState(
                items = emptyList(),
                toolbarTitle = toolbarTitle,
                loading = false,
                error = ContactError.NO_CONTACTS_FOUND
            )

        fun createErrorNoInternetConnection(toolbarTitle: String) =
            ContactsViewState(
                items = emptyList(),
                toolbarTitle = toolbarTitle,
                loading = false,
                error = ContactError.NO_CONNECTION
            )

        fun createErrorSomethingWentWrongWithServer(toolbarTitle: String) =
            ContactsViewState(
                items = emptyList(),
                toolbarTitle = toolbarTitle,
                loading = false,
                error = ContactError.SOMETHING_WENT_WRONG_WITH_SERVER
            )

        fun createContactsSuccessfullyReceived(toolbarTitle: String, items: List<Contact>) =
            ContactsViewState(
                items = items,
                toolbarTitle = toolbarTitle,
                loading = false,
                error = ContactError.NO_ERROR
            )
    }

    enum class ContactError {
        NO_ERROR, NO_CONNECTION, SOMETHING_WENT_WRONG_WITH_SERVER, NO_CONTACTS_FOUND
    }
}