package com.example.mvi.contacts.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mvi.contacts.domain.response.Contact


const val CONTACTS_LAYOUT = com.example.mvi.R.layout.item_contact

class ContactsAdapter(private val inflater: LayoutInflater, callback: ContactsDiffUtilCallback) :
    ListAdapter<Contact, ContactViewHolder>(callback) {


    private var contactClickListener: ((Contact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = inflater.inflate(CONTACTS_LAYOUT, parent, false)
        return ContactViewHolder(view, contactClickListener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

}






