package com.route.chat_c36.ui.home_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.route.chat_c36.R
import com.route.chat_c36.model.Category
import com.route.chat_c36.model.Room

class RoomsAdapter(var items: List<Room>?) :RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        val category = Category.getCategoryFromId(item!!.categoryId)
        holder.roomImageView.setImageResource(category.imageId)
        holder.roomTitle.text = item.title
        holder.itemView.setOnClickListener {
            onRoomClick?.onItemClick(position, item)
        }
    }
    var onRoomClick: OnRoomClick? = null
    override fun getItemCount(): Int {
        return items?.size?:0
    }
    interface OnRoomClick{
        fun onItemClick(pos: Int, room: Room)
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val roomImageView = itemView.findViewById<ImageView>(R.id.room_image)
        val roomTitle = itemView.findViewById<TextView>(R.id.room_title)
    }
}