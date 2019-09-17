package com.example.mvi.contacts.data

import com.example.mvi.contacts.domain.ContactSearchRepository
import com.example.mvi.contacts.domain.response.Contact
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class FakeDataProvider() : ContactSearchRepository {

    override fun getFilteredContacts(text: String): Observable<List<Contact>> {
        return Observable.just(getStubContacts())
            .map { contacts ->
                contacts.filter { contact ->
                    val searchFields = getSearcbFields(contact)
                        .trim()
                        .toLowerCase()
                    val searchText = text.trim().toLowerCase()
                    searchFields.contains(searchText)
                }
            }
    }

    private fun getSearcbFields(contact: Contact) =
        (contact.name + contact.secondName)


    override fun getAllContacts(): Observable<List<Contact>> {
        return Observable.just(
            getStubContacts()
        ).delay(3, TimeUnit.SECONDS)
    }

    private fun getStubContacts(): List<Contact> {
        return listOf(
            Contact(
                name = "Ivan",
                secondName = "Ivanov",
                photo = "",
                id = 1
            ),
            Contact(
                name = "Sergey",
                secondName = "Sergeev",
                photo = "",
                id = 2
            ),
            Contact(
                name = "Alexey",
                secondName = "Ivanov",
                photo = "",
                id = 3
            ),
            Contact(
                name = "Alexey",
                secondName = "Sergeev",
                photo = "",
                id = 4
            ),
            Contact(
                name = "Dmitry",
                secondName = "Dmitriev",
                photo = "",
                id = 5
            ),
            Contact(
                name = "Elena",
                secondName = "Lapshina",
                photo = "",
                id = 6
            ),
            Contact(
                name = "Bojack",
                secondName = "Horseman",
                photo = "",
                id = 7
            ),
            Contact(
                name = "Bojack",
                secondName = "Aleeev",
                photo = "",
                id = 8
            ),
            Contact(
                name = "Alexey",
                secondName = "Bojack",
                photo = "",
                id = 9
            ),
            Contact(
                name = "Sergey",
                secondName = "Glinka",
                photo = "",
                id = 10
            ),
            Contact(
                name = "Airbus",
                secondName = "A320",
                photo = "",
                id = 11
            ),
            Contact(
                name = "Boeing",
                secondName = "737",
                photo = "",
                id = 12
            ),
            Contact(
                name = "Airbus",
                secondName = "A380",
                photo = "",
                id = 1213
            ),
            Contact(
                name = "Jorge",
                secondName = "Boeing",
                photo = "",
                id = 14
            ),
            Contact(
                name = "Time",
                secondName = "Travel",
                photo = "",
                id = 1415
            ),
            Contact(
                name = "Travel",
                secondName = "Secondary",
                photo = "",
                id = 16
            ),
            Contact(
                name = "Boris",
                secondName = "Begunov",
                photo = "",
                id = 1617
            ),
            Contact(
                name = "Ivan",
                secondName = "Mikhailov",
                photo = "",
                id = 16178
            ),
            Contact(
                name = "Dmitry",
                secondName = "Ovchan",
                photo = "",
                id = 161789
            ),

            Contact(
                name = "Sam",
                secondName = "Way",
                photo = "",
                id = 16178920
            ),
            Contact(
                name = "Jorge",
                secondName = "Samokhodkin",
                photo = "",
                id = 1617892020
            ),
            Contact(
                name = "Unknown",
                secondName = "Contact",
                photo = "",
                id = 16173
            ),
            Contact(
                name = "Basta",
                secondName = "Sansara",
                photo = "",
                id = 161
            ),
            Contact(
                name = "Bojack",
                secondName = "Horseman",
                photo = "",
                id = 1617892
            ),
            Contact(
                name = "Alexander",
                secondName = "Linov",
                photo = "",
                id = 99
            ),
            Contact(
                name = "Sergey",
                secondName = "Garbar",
                photo = "",
                id = 98
            ),
            Contact(
                name = "Pavel",
                secondName = "Epifanov",
                photo = "",
                id = 97
            ),
            Contact(
                name = "Alena",
                secondName = "Pavshina",
                photo = "",
                id = 96
            ),
            Contact(
                name = "Kak v luchih",
                secondName = "doman Landona",
                photo = "",
                id = 95
            )
        )
    }
}