package com.cranked.androidapp

import androidx.lifecycle.MutableLiveData
import com.cranked.androidcorelibrary.viewmodel.BaseViewModel

class MainViewModel: BaseViewModel() {
    var counter = 0
    val stringText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun increaseCounter() {
        println("butona basıldı")
        counter++
        stringText.postValue("Counter :" + counter.toString())
    }
}