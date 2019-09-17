package com.example.mvi.common.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject


/**
 * Main idea was
 */


interface ViewState

interface Action


abstract class MiddleWare<A : Action, V : ViewState> {
    abstract fun bind(action: Observable<A>): Observable<A>
}


interface Store<A : Action, V : ViewState> {

    /**
     * bind all actions with middleware
     */
    fun wire(): Disposable

    /**
     * bind only ui actions to all actions
     */
    fun bind(uiAction: Observable<A>): Disposable

    fun observeViewState(): Observable<V>
}

class DefaultStore<A : Action, V : ViewState>(
    private val reducer: Reducer<A, V>,
    private val middleWare: MiddleWare<A, V>,
    initialState: V
) : Store<A, V> {
    private val allActions: PublishSubject<A> = PublishSubject.create()
    private val states: BehaviorSubject<V> = BehaviorSubject.createDefault(initialState)

    override fun wire(): Disposable {
        val compositeDisposable = CompositeDisposable()

        compositeDisposable += middleWare.bind(allActions)
            .subscribe(allActions::onNext)

        compositeDisposable +=
            allActions
                .withLatestFrom(states) { action, viewState -> reducer.reduce(viewState, action) }
                .subscribe(states::onNext)

        return compositeDisposable
    }

    override fun bind(uiAction: Observable<A>): Disposable {
        return uiAction.subscribe(allActions::onNext)
    }

    override fun observeViewState(): Observable<V> = states.hide()
}

interface Reducer<A : Action, V : ViewState> {
    fun reduce(viewState: V, action: A): V
}