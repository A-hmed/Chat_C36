package com.route.chat_c36.ui.login_activity

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.route.chat_c36.Data
import com.route.chat_c36.firebase.getUserFromFirestore
import com.route.chat_c36.model.AppUser
import java.lang.Exception
import kotlin.coroutines.coroutineContext

class LoginViewViewModel: ViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val emailError = ObservableField<String?>()
    val passwordError = ObservableField<String?>()
    val messageLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val auth = Firebase.auth
    var navigator: Navigator? = null
    fun signIn(){
     if(validate()){
         //todo: create your account
         isLoadingLiveData.value = true
        auth.signInWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener {task->
                isLoadingLiveData.value = false
                if(!task.isSuccessful){
                   Log.e("signIn",task.exception.toString())
                    messageLiveData.value = task.exception!!.message
                }else{
                   // user created his account succesfuly
                    getUserFromFirestore(task.result.user?.uid?:"",
                     onSuccess = object :OnSuccessListener<DocumentSnapshot>{
                         override fun onSuccess(docSnapshot: DocumentSnapshot?) {
                           isLoadingLiveData.value = false

                             val currentUser =  docSnapshot?.toObject(AppUser::class.java)
                             Data.currentUser = currentUser
                             Log.e("sucess","user first name is ${currentUser?.firstName}")
                             navigator?.navigateToHome()
                         }
                     }, onFailure = object :OnFailureListener{
                            override fun onFailure(p0: Exception) {
                                isLoadingLiveData.value = false
                                 messageLiveData.value = p0.message
                            }
                        });
                    Log.e("sign in", "user logined")
                }
        }
     }
    }
    fun navigateToRegisterScreen(){
        navigator?.navigateToHome()
    }
    fun validate():Boolean{
        var valid = true
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
        return valid
    }
}