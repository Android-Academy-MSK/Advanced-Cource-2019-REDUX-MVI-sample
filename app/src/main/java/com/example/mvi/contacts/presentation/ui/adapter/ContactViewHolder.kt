package com.example.mvi.contacts.presentation.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvi.R
import com.example.mvi.contacts.domain.response.Contact

class ContactViewHolder(
    itemView: View,
    private val contactClickListener: ((Contact) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {


    private val tvName = itemView.findViewById<TextView>(R.id.tv_name)
    private val tvPhone = itemView.findViewById<TextView>(R.id.tv_phone)

    fun bindItem(contact: Contact) {
        tvName.text = contact.name
        tvPhone.text = contact.secondName
        itemView.setOnClickListener { contactClickListener?.invoke(contact) }
    }

}