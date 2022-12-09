package com.route.chat_c36.ui.chat_activity


import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.remote.WatchChange
import com.google.firebase.ktx.Firebase
import com.route.chat_c36.R
import com.route.chat_c36.databinding.ActivityChatBinding
import com.route.chat_c36.databinding.ActivityHomeBinding
import com.route.chat_c36.firebase.getAllRooms
import com.route.chat_c36.model.Message
import com.route.chat_c36.model.Room
import com.route.chat_c36.ui.add_room.AddRoomActivity
import com.route.chat_c36.ui.register_activity.BaseActivity

class ChatActivity : BaseActivity<ChatViewModel, ActivityChatBinding>() {
    var messagesList = mutableListOf<Message>()
    var adapter: MessagesAdapter = MessagesAdapter(messagesList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel
        val roomId = intent.getStringExtra("roomId")
        viewModel.roomId = roomId ?: ""
        initViews()
        subscribeToLiveData()
        subscribeToMessagesCollection()
    }

    fun subscribeToLiveData() {
        viewModel.isLoadingLiveData.observe(this) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        viewModel.messageLiveData.observe(this) {
            showError(title = "Error", message = it)
        }
    }

    fun subscribeToMessagesCollection() {
        val fireStore = Firebase.firestore
        fireStore.collection(Room.COLLECTION_NAME).document(viewModel.roomId)
            .collection(Message.COLLECTION_NAME).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("subscribeToMessagesCollection", "Listen failed.", e)
                    showError("Error", "Please try again later")
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    for (docChange in snapshot.documentChanges) {
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                val message = docChange.document.toObject(Message::class.java)
                                Log.e("subscribeToMessagesCollection",
                                    "adding message to list with content ${message.content}")
                                messagesList.add(message)
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e("subscribeToMessagesCollection", "Current data: null")
                }
            }
    }

    fun initViews() {
        viewDataBinding.messagesRecyclerview.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
        viewDataBinding.messagesRecyclerview.adapter = adapter
    }

    override fun getLayOutFile(): Int {
        return R.layout.activity_chat
    }

    override fun initViewModel(): ChatViewModel {
        return ViewModelProvider(this).get(ChatViewModel::class.java)
    }
}