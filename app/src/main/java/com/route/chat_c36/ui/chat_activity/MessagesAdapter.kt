package com.route.chat_c36.ui.chat_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.route.chat_c36.Data
import com.route.chat_c36.R
import com.route.chat_c36.databinding.ItemMessageReceiveBinding
import com.route.chat_c36.databinding.ItemMessageSentBinding
import com.route.chat_c36.model.Message

class MessagesAdapter(var items: MutableList<Message>):RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>(){

    override fun getItemViewType(position: Int): Int {
        val message = items[position]
        if(message.senderId == Data.currentUser!!.id){
            return 0
        }else{
            return 1
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        if(viewType == 0){
         val binding: ItemMessageSentBinding = DataBindingUtil.inflate(
             LayoutInflater.from(parent.context),
             R.layout.item_message_sent,
             parent,
             false
         )
         return SentMessageViewHolder(binding)
        }else {
            val binding: ItemMessageReceiveBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_message_receive,
                parent,
                false
            )
            return ReceivedMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = items.get(position)
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind(message: Message)
    }
    class SentMessageViewHolder(val binding: ItemMessageSentBinding):MessageViewHolder(binding.root){
        override fun bind(message: Message) {
            binding.message = message
        }
    }
    class ReceivedMessageViewHolder(val binding: ItemMessageReceiveBinding):MessageViewHolder(binding.root){
        override fun bind(message: Message) {
            binding.message = message
        }
    }

}