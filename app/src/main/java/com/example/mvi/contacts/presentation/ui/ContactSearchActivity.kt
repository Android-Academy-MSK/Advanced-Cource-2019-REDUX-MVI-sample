package com.example.mvi.contacts.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi.R
import com.example.mvi.common.base.ui.BaseActivity
import com.example.mvi.contacts.ContactsAction
import com.example.mvi.contacts.ContactsViewState
import com.example.mvi.contacts.presentation.mvi.ContactsViewModel
import com.example.mvi.contacts.presentation.ui.adapter.ContactsAdapter
import com.example.mvi.contacts.presentation.ui.adapter.ContactsDiffUtilCallback
import kotlinx.android.synthetic.main.activity_contact_search.*
import org.koin.android.viewmodel.ext.android.viewModel


class ContactSearchActivity : BaseActivity() {

    companion object {
        private const val LAYOUT = R.layout.activity_contact_search
    }

    private val searchViewModel: ContactsViewModel by viewModel()
    private lateinit var adapter: ContactsAdapter

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        setupUi()
        setupUx()
    }

    private fun setupUx() {

        toolbar.inflateMenu(R.menu.action_search)
        val menuItem = toolbar.menu.findItem(R.id.menu_search)

        searchView = menuItem.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.onAction(
                    ContactsAction.Search(
                        newText ?: ""
                    )
                )
                return true
            }
        })
    }


    override fun onBackPressed() {
        if (searchView?.isIconified == false) {
            searchView?.isIconified = true
        } else {
            super.onBackPressed()
        }
    }


    private fun setupUi() {
        adapter = ContactsAdapter(LayoutInflater.from(this), ContactsDiffUtilCallback())
        rv_contacts.layoutManager = LinearLayoutManager(this)
        (rv_contacts.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        rv_contacts.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        searchViewModel.onAttach(fisrtStart)
        searchViewModel.observeViewState()
            .subscribeTillStop(::renderUi)
    }


    override fun onStop() {
        searchViewModel.onDetach()
        super.onStop()
    }

    private fun renderUi(state: ContactsViewState) {
        toolbar.title = state.toolbarTitle
        renderLoading(state)
        renderContacts(state)
        renderError(state)
    }

    private fun renderError(state: ContactsViewState) {
        when (state.error) {
            ContactsViewState.ContactError.SOMETHING_WENT_WRONG_WITH_SERVER -> {
                lt_error.visibility = View.VISIBLE
                tv_error.text = getString(R.string.error_server)
            }
            ContactsViewState.ContactError.NO_CONNECTION -> {
                lt_error.visibility = View.VISIBLE
                tv_error.text = getString(R.string.no_connection)
            }
            ContactsViewState.ContactError.NO_CONTACTS_FOUND -> {
                lt_error.visibility = View.VISIBLE
                tv_error.text = getString(R.string.no_contacts)
            }

            ContactsViewState.ContactError.NO_ERROR -> {
                lt_error.visibility = View.GONE
                tv_error.text = ""
            }
        }
    }

    private fun renderContacts(state: ContactsViewState) {
        val items = state.items
        if (state.items.isEmpty()) {
            rv_contacts.visibility = View.GONE
        } else {
            rv_contacts.visibility = View.VISIBLE
        }
        adapter.submitList(items)
    }

    private fun renderLoading(state: ContactsViewState) {
        if (state.loading) {
            lt_progress.visibility = View.VISIBLE
        } else {
            lt_progress.visibility = View.GONE
        }
    }
}


fun TextView.safeSetText(text: String) {
    if (getText() != text) {
        setText(text)
    }
}


