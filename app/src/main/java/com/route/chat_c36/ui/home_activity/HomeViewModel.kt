package com.route.chat_c36.ui.home_activity

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.coroutineContext

class HomeViewModel: ViewModel() {
   var navigator: Navigator? = null
 fun navigateToAddRoomScreen(){
     navigator!!.navigateToAddRoom()
 }
}