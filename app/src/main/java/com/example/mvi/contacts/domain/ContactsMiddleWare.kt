package com.example.mvi.contacts.domain

import com.example.mvi.contacts.domain.response.Contact
import com.example.mvi.contacts.ContactsAction
import com.example.mvi.contacts.ContactsViewState
import com.example.mvi.common.base.MiddleWare
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ContactsMiddleWare(private val repository: ContactSearchRepository) :
    MiddleWare<ContactsAction, ContactsViewState>() {


    override fun bind(actionStream: Observable<ContactsAction>): Observable<ContactsAction> {

        return actionStream
            .filter(::canContinue)
            .flatMap { filteredAction ->
                when (filteredAction) {
                    is ContactsAction.Search -> searchContacts(filteredAction)
                    is ContactsAction.ForceUpdate -> allContacts()
                    else -> throw IllegalStateException("Action not processed")
                }
            }
    }

    private fun allContacts(): Observable<ContactsAction> {
        return repository.getAllContacts()
            .map(::convertToResult)
            .onErrorReturn { throwable -> ContactsAction.ContactsError(throwable) }
            .subscribeOn(Schedulers.io())
            .startWith(ContactsAction.Loading)
    }

    private fun searchContacts(action: ContactsAction.Search): Observable<ContactsAction> {
        return repository.getFilteredContacts(action.search)
            .map(::convertToResult)
            .onErrorReturn { throwable -> ContactsAction.ContactsError(throwable) }
            .subscribeOn(Schedulers.io())
    }

    private fun convertToResult(contacts: List<Contact>): ContactsAction {
        return if (contacts.isEmpty()) {
            ContactsAction.ContactsDontFound
        } else {
            ContactsAction.ContactsLoaded(contacts)
        }
    }


    private fun canContinue(action: ContactsAction) =
        action is ContactsAction.Search || action is ContactsAction.ForceUpdate

}



