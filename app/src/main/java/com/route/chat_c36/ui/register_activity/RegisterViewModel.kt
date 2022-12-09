package com.route.chat_c36.ui.register_activity

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.route.chat_c36.firebase.addToFireStore
import com.route.chat_c36.model.AppUser
import java.lang.Exception

class RegisterViewModel: ViewModel() {
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val firstNameError = ObservableField<String>()
    val lastNameError = ObservableField<String?>()
    val emailError = ObservableField<String?>()
    val passwordError = ObservableField<String?>()
    val messageLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    var navigator: Navigator? = null
    val auth = Firebase.auth

    fun createAccount(){
     if(validate()){
         //todo: create your account
         isLoadingLiveData.value = true
        auth.createUserWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener {task->
                if(!task.isSuccessful){
                    isLoadingLiveData.value = false
                   Log.e("createAccount",task.exception.toString())
                    messageLiveData.value = task.exception!!.message
                }else{
                   // user created his account succesfuly
                    val user =
                        AppUser(firstName = firstName.get(),
                            lastName = lastName.get(),
                            email = email.get(),
                            id = task.result.user?.uid
                            )
                    addToFireStore(user, successListener = {
                        isLoadingLiveData.value=false
                        navigator?.navigateToHome()
                        Log.e("onSuccess","added user data to fireStore")
                    }) {
                        Log.e("onFailure",it.message!!)
                        isLoadingLiveData.value=false
                      messageLiveData.value = it.message
                    }
                }
        }
     }
    }
    fun validate():Boolean{
        var valid = true
        if(firstName.get().isNullOrBlank()){
           valid = false
            firstNameError.set("Please enter valid name")
        }else{
            firstNameError.set(null)
        }
        if(email.get().isNullOrBlank()){
            valid = false
            emailError.set("Please enter valid email")
        }else{
            emailError.set(null)
        }
        if(password.get().isNullOrBlank()){
            valid = false
            passwordError.set("Please valid password")
        }else{
            passwordError.set(null)
        }
        if(lastName.get().isNullOrBlank()){
            valid = false
            lastNameError.set("Please enter valid name")
        }else{
            lastNameError.set(null)
        }
        return valid
    }
}