package com.route.chat_c36.model

import android.view.inspector.InspectionCompanion

data class Room(
    var id:String? ="",
    val title: String = "",
    val description: String = "",
    val categoryId: String = "",
){
    companion object{
        val COLLECTION_NAME = "rooms"
    }
}
