package com.example.kul.projektkoncowy

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class Purchase {
    var id:Int = 0;
    val name : String?
    val price : Double?
    val date : LocalDate?



    constructor(name: String?, price: Double?, date: LocalDate?) {
        this.name = name
        this.price = price
        this.date = date
    }

    constructor(id: Int,name: String?, price: Double?, date: LocalDate?) {
        this.id = id
        this.name = name
        this.price = price
        this.date = date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(name: String?, price: Double?, date: String?) {
        this.id = id
        this.name = name
        this.price = price
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        this.date = LocalDate.parse(date,formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate() : String{
        if (date != null) {
            if(date.monthValue<9 && date.dayOfMonth < 10)
            return "${date.year}-0${date.monthValue}-0${date.dayOfMonth}"
            else if(date.monthValue<9)
                return "${date.year}-0${date.monthValue}-${date.dayOfMonth}"
            else if(date.dayOfMonth<10)
                return "${date.year}-${date.monthValue}-0${date.dayOfMonth}"
            else
                return "${date.year}-${date.monthValue}-${date.dayOfMonth}"
        }
        return ""
    }

    override fun toString(): String {
        return "Purchase(name=$name)"
    }


}