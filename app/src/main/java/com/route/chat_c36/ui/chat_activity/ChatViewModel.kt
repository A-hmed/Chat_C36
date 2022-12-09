package com.route.chat_c36.ui.chat_activity

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.route.chat_c36.Data
import com.route.chat_c36.firebase.addMessageToFireStore
import com.route.chat_c36.model.Message
import kotlin.coroutines.coroutineContext

class ChatViewModel: ViewModel() {
    val messageLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val messageContent = MutableLiveData<String>()
    var roomId: String= ""
    fun sendMessage(){
          if(messageContent.value.isNullOrBlank()) return
        val message = Message(
            senderId = Data.currentUser!!.id!!,
            content = messageContent.value!!,
            senderName = Data.currentUser!!.firstName!!,
            time = Timestamp.now()
        )
         addMessageToFireStore(
             roomId = roomId,
             message,
             {
             messageContent.value = ""
             },{
                 messageLiveData.value = it.message
             }
         )
    }
}