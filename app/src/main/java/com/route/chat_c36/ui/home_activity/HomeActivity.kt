package com.route.chat_c36.ui.home_activity


import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.route.chat_c36.R
import com.route.chat_c36.databinding.ActivityHomeBinding
import com.route.chat_c36.firebase.getAllRooms
import com.route.chat_c36.model.Room
import com.route.chat_c36.ui.add_room.AddRoomActivity
import com.route.chat_c36.ui.chat_activity.ChatActivity
import com.route.chat_c36.ui.register_activity.BaseActivity

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(), Navigator {
    var roomsList = mutableListOf<Room>()
    lateinit var adapter: RoomsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        viewDataBinding.vm = viewModel
        initViews()


    }

    fun initViews() {
        adapter = RoomsAdapter(roomsList)
        adapter.onRoomClick = object: RoomsAdapter.OnRoomClick{
            override fun onItemClick(pos: Int, room: Room) {
                val intent = Intent(baseContext, ChatActivity::class.java)
                intent.putExtra("roomId",room.id)
                startActivity(intent)
            }
        }
        viewDataBinding.roomsRecyclerView.adapter = adapter
        viewDataBinding.roomsRecyclerView.layoutManager = GridLayoutManager(
            this, 2,
            RecyclerView.VERTICAL, false
        )
    }

    fun getRooms() {
        showLoading()
        getAllRooms({
            hideLoading()
            it.documents.forEach { doc ->
                val room = doc.toObject(Room::class.java)
                roomsList.add(room!!)
            }
            adapter.notifyDataSetChanged()
        }, {
            hideLoading()
            showError(title = "Error", message = it.message ?: "Error while loading data")
        })
    }

    override fun onResume() {
        super.onResume()
        getRooms()
    }
    override fun getLayOutFile(): Int {
        return R.layout.activity_home
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun navigateToAddRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
    }

}