package com.xiaoyu.verificationcodeinputview

import androidx.databinding.BindingAdapter

object VerificationCodeInputViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:on_input_finish", requireAll = false)
    fun bindInputFinishCallback(
        view: VerificationCodeInputView,
        onInputFinishCallback: VerificationCodeInputView.OnInputFinishCallback?
    ) {
        if (view.onInputFinishCallback != onInputFinishCallback) {
            view.onInputFinishCallback = onInputFinishCallback
        }
    }
}