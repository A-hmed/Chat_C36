package com.route.chat_c36.ui.add_room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.route.chat_c36.R
import com.route.chat_c36.model.Category

class SpinnerAdapter(var items: List<Category>) :BaseAdapter(){
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(index: Int): Any {
         return items[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getView(index: Int, view: View?, parent: ViewGroup?): View {
        var view = view
        var viewHolder: ViewHolder
        if(view == null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_spinner,parent, false)
            viewHolder = ViewHolder(itemView = view)
            view.tag =viewHolder
        }else{
            viewHolder = view.tag as ViewHolder
        }
        val item = items.get(index)
        viewHolder.spinnerImage.setImageResource(item.imageId)
        viewHolder.spinnertitle.text = item.title
        return view!!
    }
    class ViewHolder(itemView: View) {
        var spinnerImage = itemView.findViewById<ImageView>(R.id.spinner_item_image)
        var spinnertitle = itemView.findViewById<TextView>(R.id.spinner_item_title)
    }

}