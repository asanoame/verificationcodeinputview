package com.xiaoyu.verificationcodeframe

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val code = ObservableField<String>()

    val showToast = MutableLiveData<String>()

    fun onInputFinish(code: String) {
        showToast.postValue(code)
    }
}