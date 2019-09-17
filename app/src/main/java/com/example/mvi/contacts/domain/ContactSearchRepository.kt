package com.example.mvi.contacts.domain

import com.example.mvi.contacts.domain.response.Contact
import io.reactivex.Observable

interface ContactSearchRepository {

    fun getAllContacts(): Observable<List<Contact>>

    fun getFilteredContacts(text: String): Observable<List<Contact>>
}