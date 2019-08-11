package com.example.androidbase.common.presentationLayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<T> : ViewModel() {

    private val mutableLiveData: MutableLiveData<T> = MutableLiveData(getInitialState())

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    abstract fun getInitialState(): T

    fun postState(state: T) {
        mutableLiveData.postValue(state)
    }

    fun getCurrentState(): T = mutableLiveData.value ?: getInitialState()

    fun getLiveData(): LiveData<T> = mutableLiveData

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}