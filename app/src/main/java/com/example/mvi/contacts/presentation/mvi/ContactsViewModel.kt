package com.example.mvi.contacts.presentation.mvi

import androidx.lifecycle.ViewModel
import com.example.mvi.common.base.Store
import com.example.mvi.contacts.ContactsAction
import com.example.mvi.contacts.ContactsViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.PublishSubject


class ContactsViewModel(private val store: Store<ContactsAction, ContactsViewState>) :
    ViewModel() {

    private val uiActions = PublishSubject.create<ContactsAction>()
    private var actionsDisposable: Disposable = Disposables.empty()
    private var wiringDisposable: Disposable = Disposables.empty()


    init {
        wiringDisposable = store.wire()
    }


    fun observeViewState() = store.observeViewState()
        .observeOn(AndroidSchedulers.mainThread())

    fun onAttach(firstAttach: Boolean) {
        actionsDisposable = store.bind(uiActions.hide())
        if (firstAttach) {
            onAction(ContactsAction.ForceUpdate)
        }
    }

    fun onDetach() {
        actionsDisposable.dispose()
    }


    fun onAction(action: ContactsAction) {
        postAction(action)
    }

    override fun onCleared() {
        wiringDisposable.dispose()
        super.onCleared()
    }


    private fun postAction(action: ContactsAction) {
        uiActions.onNext(action)
    }
}
