package com.route.chat_c36.model

import com.google.firebase.Timestamp

data class Message(
    var id:String? = "",
    val senderId: String = "",
    val content: String = "",
    val senderName: String = "",
    val time: Timestamp = Timestamp.now()
){
    companion object{
        const val COLLECTION_NAME = "messages"
    }
}
