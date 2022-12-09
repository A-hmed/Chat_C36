package com.route.chat_c36.ui.add_room

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.route.chat_c36.firebase.addRoomToFireStore
import com.route.chat_c36.model.Room


class AddRoomViewModel: ViewModel() {
    var title = MutableLiveData<String>()
    var titleError = MutableLiveData<String?>()
    var description = MutableLiveData<String>()
    var descriptionError = MutableLiveData<String?>()
    var selectedCategoryId = "sports"
    val messageLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()
    var navigator: Navigator? = null
  fun createRoom(){
    if(! validate()) return;
    isLoadingLiveData.value = true
    var room = Room(
      title = title.value!!,
      description = description.value!!,
      categoryId = selectedCategoryId
    )
    addRoomToFireStore(room,{
      isLoadingLiveData.value = false
      navigator?.finishAddRoom()
    },{
      isLoadingLiveData.value = false
     messageLiveData.value = it.message
    })
  }
  fun validate():Boolean{
    var valid = true
    if(title.value.isNullOrBlank()){
      valid = false
      titleError.value = "Please enter valid title"
    }else{
      titleError.value = null
    }
    if(description.value.isNullOrBlank()){
      valid = false
      descriptionError.value = "Please enter valid description"
    }else{
      descriptionError.value = null
    }
      Log.e()
    return valid
  }
}