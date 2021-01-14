package com.example.kul.projektkoncowy

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.security.auth.Subject


class Adapter(private val values: List<Purchase>, private val adapterCallback : (Purchase) -> Unit) : RecyclerView.Adapter<Adapter.ItemHolder>() {

    override fun getItemCount() = values.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        return ItemHolder(itemView)
    }

    class ItemHolder : RecyclerView.ViewHolder{
        var product : TextView? = null
        var price : TextView? = null
        var date : TextView? = null
        var image : ImageView? = null

        constructor(itemView: View) : super(itemView){
            product = itemView.findViewById(R.id.name_list_item)
            price = itemView.findViewById(R.id.price)
            date = itemView.findViewById(R.id.date)
            image = itemView.findViewById(R.id.image)
           }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.product?.text  = values[position].name
        holder.price?.text = values[position].price.toString()
        holder.date?.text = values[position].date.toString()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val stringDate = holder.date?.text.toString()
        holder.image?.setOnClickListener{adapterCallback(Purchase(holder.product?.text.toString(),
                holder.price?.text.toString().toDouble(),
                LocalDate.parse(stringDate, formatter)))}
    }

}//values[position].price.toString() + R.string.money values[position].price.toString() +
