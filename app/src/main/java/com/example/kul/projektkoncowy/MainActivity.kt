package com.example.kul.projektkoncowy
import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    private var lista_purchas = listOf<Purchase>()

    private val newAvtivity = View.OnClickListener{callAddActivityWithReturnParameters()}
    private val buttonStatListener = View.OnClickListener { startStatisticsActivity() }

    private fun startStatisticsActivity() {
        val intent = Intent(this, StatisticsActivity::class.java)
        startActivity(intent)
    }

    private fun adapterItemClick(item : Purchase){
        startShowPurchaseActivity(item)
    }

    fun startShowPurchaseActivity(item : Purchase){
        val intent = Intent(this,ShowPurchaseActivity::class.java)
        intent.putExtra("PRODUCT",item.name)
        intent.putExtra("PRICE",item.price.toString())
        intent.putExtra("DATE",item.date.toString())
        startActivityForResult(intent,3)
    }

    fun callAddActivityWithReturnParameters(){
        val intentWithResult = Intent(this,AddPurchase::class.java)
        intentWithResult.putExtra("EDIT","false")
        startActivityForResult(intentWithResult,2)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                if (data != null) {
                    if(!data.getStringExtra("CHANEL").toString().toBoolean()) {
                        val name = data.getStringExtra("NAME_PURCHASE")
                        val price = data.getStringExtra("PRICE_PURCHASE")?.toDouble()
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val stringDate = data.getStringExtra("DATE_PURCHASE")
                        val date = LocalDate.parse(stringDate, formatter)
                        val purchase = Purchase(name, price, date)
                        addPurchase(purchase)
                    }
                }
            }
        }else if(requestCode == 3){
            if(resultCode == Activity.RESULT_OK){
                if (data != null) {
                    if(data.getStringExtra("EDIT").toString().toBoolean()) {
                        val dataBaseHandler = DataBaseHelper(this,null,null,1)
                        lista_purchas = dataBaseHandler.all()
                        showListPurchase()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addPurchase(purchase: Purchase) {
        val dataBaseHandler = DataBaseHelper(this,null,null,1)
        dataBaseHandler.addPurchasse(purchase)
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
        lista_purchas = dataBaseHandler.all()
        showListPurchase()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showListPurchase() {
        val dataBaseHandler = DataBaseHelper(this,null,null,1)
        lista_purchas = dataBaseHandler.all()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(lista_purchas){item->adapterItemClick(item)}
        if(lista_purchas.isEmpty()) findViewById<TextView>(R.id.listIsEmpty).visibility = View.VISIBLE
        else findViewById<TextView>(R.id.listIsEmpty).visibility = View.INVISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showListPurchase()

        val add = findViewById<FloatingActionButton>(R.id.addPurchase)
        add.setOnClickListener(newAvtivity)

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        val button_stat = findViewById<Button>(R.id.stat)
        button_stat.setOnClickListener(buttonStatListener)

    }

}