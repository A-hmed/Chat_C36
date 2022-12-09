package com.route.chat_c36.model

data class AppUser(
    var id: String? ="",
    var firstName: String?= "",
    var lastName: String? = "",
    var email: String? = ""
){
    companion object{
       val COLLECTION_NAME="users"
    }
}
