package com.route.chat_c36

import com.google.firebase.Timestamp

fun Timestamp.format(): String{
    return "${this.toDate()}"
}