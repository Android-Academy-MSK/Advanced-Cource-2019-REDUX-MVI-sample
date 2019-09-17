package com.example.mvi.contacts.domain.response

data class Contact(
    val id: Int,
    val name: String,
    val secondName: String,
    val photo: String
)