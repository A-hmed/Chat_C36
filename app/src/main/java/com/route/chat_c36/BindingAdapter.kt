package com.route.chat_c36.ui.register_activity

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:error")
fun setError(textInput: TextInputLayout, errorMessage: String?){
    textInput.error = errorMessage
}