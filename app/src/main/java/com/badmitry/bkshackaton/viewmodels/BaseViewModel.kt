package com.badmitry.bkshackaton.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.badmitry.domain.entities.EventArgs
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(app: Application) : AndroidViewModel(app) {

    private val compositeDisposables = CompositeDisposable()

    private val progressData by lazy {
        MutableLiveData<EventArgs<Boolean>>()
    }

    private val errorData by lazy {
        MutableLiveData<EventArgs<Throwable>>()
    }

    override fun onCleared() {
        compositeDisposables.clear()
        super.onCleared()
    }

    protected fun onSubscribe(@Suppress("UNUSED_PARAMETER") disposable: Disposable) {
        progressData.value = EventArgs(true)
    }

    protected fun onFinally() {
        progressData.value = EventArgs(false)
    }

    protected fun onError(e: Throwable) {
        errorData.value = EventArgs(e)
    }

    fun observe(
        owner: LifecycleOwner,
        onProgress: (EventArgs<Boolean>) -> Unit,
        onError: (EventArgs<Throwable>) -> Unit
    ) {
        errorData.observe(owner, Observer { onError(it!!) })
        progressData.observe(owner, Observer { onProgress(it!!) })
    }
}