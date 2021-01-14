package com.example.kul.projektkoncowy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class PurchaseAdapter {


    class PurchaseViewHolder : RecyclerView.ViewHolder {

        val product : TextView
        val price : TextView
        val date : TextView
        val image : ImageView

        constructor(itemView: View) : super(itemView){
            this.product = itemView.findViewById(R.id.name_list_item)
            this.price = itemView.findViewById(R.id.price)
            this.date = itemView.findViewById(R.id.date)
            this.image = itemView.findViewById(R.id.image)
        }



    }
}