package com.badmitry.domain.interactors

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseInteractor<T, Params>(
    private val scheduler: Scheduler,
    private val postScheduler: Scheduler
) {

    private val compositeDisposable = CompositeDisposable()

    private var params: Params? = null
    private var doOnSubscribe: ((Disposable) -> Unit)? = null
    private var doOnFinally: (() -> Unit)? = null
    private var onSuccess: ((T) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null

    abstract fun createSingle(params: Params): Single<T>

    fun dispose() {
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
        params = null
        doOnSubscribe = null
        doOnFinally = null
        onSuccess = null
        onError = null
    }

    operator fun invoke(
        params: Params,
        doOnSubscribe: ((Disposable) -> Unit)?,
        doOnFinally: (() -> Unit)?,
        onSuccess: ((T) -> Unit)?,
        onError: ((Throwable) -> Unit)?
    ) {
        this.params = params
        this.doOnSubscribe = doOnSubscribe
        this.doOnFinally = doOnFinally
        this.onSuccess = onSuccess
        this.onError = onError

        invokeRequest()
    }

    private fun invokeRequest() {
        params?.let {
            compositeDisposable.add(
                createSingle(it)
                    .subscribeOn(scheduler)
                    .observeOn(postScheduler)
                    .doOnSubscribe(doOnSubscribe)
                    .doFinally(doOnFinally)
                    .subscribe(onSuccess, onError)
            )
        }
    }
}
