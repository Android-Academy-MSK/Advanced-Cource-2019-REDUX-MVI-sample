package com.example.mvi.contacts.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mvi.contacts.domain.response.Contact

class ContactsDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact) = oldItem == newItem
}




