package com.example.kul.projektkoncowy

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.number.IntegerWidth
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DataBaseHelper (context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME,factory, DATABASE_VERSION) {

    val TABLE_PURCHASE = "purchase"
    val COLUMN_ID = "purchase_id"
    val COLUMN_PURCHASE_NAME = "purchase_name"
    val COLUMN_PURCHASE_PRICE = "purchase_price"
    val COLUMN_PURCHASE_DATE = "purchase_date"

    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "purchasesDataBase.db"
    }

    override fun onCreate(database: SQLiteDatabase) {
        val CREATE_PURCHASE_TABLE = ("CREATE TABLE " +
                TABLE_PURCHASE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PURCHASE_NAME + " TEXT,"
                + COLUMN_PURCHASE_PRICE + " DOUBLE,"
                + COLUMN_PURCHASE_DATE + " TEXT" + ")"
                )
        database.execSQL(CREATE_PURCHASE_TABLE)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASE)
        onCreate(database)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun addPurchasse(purchase: Purchase){
        val data = ContentValues()
        data.put(COLUMN_PURCHASE_NAME,purchase.name)
        data.put(COLUMN_PURCHASE_PRICE,purchase.price)
        data.put(COLUMN_PURCHASE_DATE,purchase.getDate())

        val database = this.writableDatabase
        database.insert(TABLE_PURCHASE,null,data)
        database.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updatePurchase(pc_1 : Purchase, ph_2 : Purchase) : Boolean{
        var result = false
        val query = "SELECT * FROM $TABLE_PURCHASE WHERE $COLUMN_PURCHASE_NAME = \"${pc_1.name}\" " + "and $COLUMN_PURCHASE_PRICE = \"${pc_1.price.toString()}\" " + "and $COLUMN_PURCHASE_DATE = \"${pc_1.getDate()}\""
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            val cv = ContentValues()
            cv.put(COLUMN_PURCHASE_NAME,ph_2.name)
            cv.put(COLUMN_PURCHASE_PRICE,ph_2.price)
            cv.put(COLUMN_PURCHASE_DATE,ph_2.getDate())
            val id = Integer.parseInt(cursor.getString(0))
            database.update(TABLE_PURCHASE,cv,COLUMN_ID + " = ?", arrayOf(id.toString()))
            cursor.close()
            result = true
        }
        database.close()
        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findPurchase(purchaseName: String, purchasePrice: String) : Purchase?{
        val query = "SELECT * FROM $TABLE_PURCHASE WHERE $COLUMN_PURCHASE_NAME = \"$purchaseName\" " + "and $COLUMN_PURCHASE_PRICE = \"$purchasePrice\""
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        var player: Purchase? = null
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            val id = Integer.parseInt(cursor.getString(0))
            val purchaseName1 = cursor.getString(1)
            val purchasePrice1 = cursor.getString(2)
            val year = cursor.getString(3).toInt()
            val month = cursor.getString(4).toInt()
            val day = cursor.getString(5).toInt()
            val date = LocalDate.of(year,month,day)
            player = Purchase(id,purchaseName1,purchasePrice1.toDouble(), date)
            cursor.close()
        }
        database.close()
        return player
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deletePurchase(ph : Purchase) : Boolean{
        var result = false
        val query = "SELECT * FROM $TABLE_PURCHASE WHERE $COLUMN_PURCHASE_NAME = \"${ph.name}\" " + "and $COLUMN_PURCHASE_PRICE = \"${ph.price}\" "+ "and $COLUMN_PURCHASE_DATE = \"${ph.getDate()}\""
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        if(cursor.moveToFirst()){
            val id = Integer.parseInt(cursor.getString(0))
            database.delete(TABLE_PURCHASE, COLUMN_ID + " = ?", arrayOf(id.toString()))
            cursor.close()
            result = true
        }
        database.close()
        return result
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun all(): List<Purchase> {
        val list_purchase = mutableListOf<Purchase>()
        val query = "SELECT * FROM $TABLE_PURCHASE"
        val database = this.writableDatabase
        val cursor = database.rawQuery(query,null)
        while(cursor.moveToNext()){
            val id = Integer.parseInt(cursor.getString(0))
            val name = cursor.getString(1)
            val price = cursor.getString(2).toDouble()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date : LocalDate = LocalDate.parse(cursor.getString(3),formatter)
            list_purchase.add(Purchase(id,name,price, date))
        }
        cursor.close()
        database.close()
        return list_purchase
    }

}