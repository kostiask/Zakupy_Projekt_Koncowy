package com.example.kul.projektkoncowy

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ShowPurchaseActivity : AppCompatActivity() {

    private var edit = false
    private  var delete = false

    private val buttonEditListener = View.OnClickListener { startEditPurchaseActivity() }
    private val buttonChanelListener = View.OnClickListener { chanelMainActivity() }
    @RequiresApi(Build.VERSION_CODES.O)
    private val buttonDeleteListener = View.OnClickListener { deletePurhase() }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deletePurhase() {
        val dataBaseHandler = DataBaseHelper(this,null,null,1)

        val product_2 = findViewById<TextView>(R.id.text_product_name).text.toString()
        val price_2 = findViewById<TextView>(R.id.price_text).text.toString()
        val date_2 = findViewById<TextView>(R.id.date_text).text.toString()
        val ph_2 = Purchase(product_2,price_2.toDouble(),date_2)
        if(dataBaseHandler.deletePurchase(ph_2)) {
            Toast.makeText(this, R.string.delete_purchase, Toast.LENGTH_SHORT).show()
            delete = true
            chanelMainActivity()
        }else{
            Toast.makeText(this, R.string.error_delete, Toast.LENGTH_SHORT).show()
        }
    }

    private fun chanelMainActivity() {
        val intent = Intent()
        if(!edit && !delete) {
            setResult(Activity.RESULT_OK, intent)
            finish()
        }else{
            intent.putExtra("EDIT", "true")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun startEditPurchaseActivity() {

        val product = findViewById<TextView>(R.id.text_product_name).text
        val price = findViewById<TextView>(R.id.price_text).text
        val date = findViewById<TextView>(R.id.date_text).text
        val intentWithRezult = Intent(this,AddPurchase::class.java)
        intentWithRezult.putExtra("EDIT","true")
        intentWithRezult.putExtra("PRODUCT",product)
        intentWithRezult.putExtra("PRICE",price)
        intentWithRezult.putExtra("DATE",date)
        startActivityForResult(intentWithRezult,2)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                if (data != null) {
                    if(!data.getStringExtra("CHANEL").toString().toBoolean()) {
                        val name = data.getStringExtra("NAME_PURCHASE")
                        val price = data.getStringExtra("PRICE_PURCHASE")
                        val stringDate = data.getStringExtra("DATE_PURCHASE")
                        findViewById<TextView>(R.id.text_product_name).setText(name)
                        findViewById<TextView>(R.id.price_text).setText(price)
                        findViewById<TextView>(R.id.date_text).setText(stringDate)
                        editPurchase()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_purchase)

        val intent = getIntent()

        findViewById<TextView>(R.id.text_product_name).setText(intent.getStringExtra("PRODUCT") )
        findViewById<TextView>(R.id.price_text).setText(intent.getStringExtra("PRICE"))
        findViewById<TextView>(R.id.date_text).setText(intent.getStringExtra("DATE"))

        val button_edit = findViewById<Button>(R.id.button_edit)
        button_edit.setOnClickListener(buttonEditListener)

        val button_chanel = findViewById<Button>(R.id.button_chanel)
        button_chanel.setOnClickListener(buttonChanelListener)

        val button_delete = findViewById<Button>(R.id.button_delete)
        button_delete.setOnClickListener(buttonDeleteListener)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun editPurchase(){
        val dataBaseHandler = DataBaseHelper(this,null,null,1)
        val intent = getIntent()
        val product = intent.getStringExtra("PRODUCT")
        val price = intent.getStringExtra("PRICE")
        val date = intent.getStringExtra("DATE")
        val ph_1 = Purchase(product,price?.toDouble(),date)
        val product_2 = findViewById<TextView>(R.id.text_product_name).text.toString()
        val price_2 = findViewById<TextView>(R.id.price_text).text.toString()
        val date_2 = findViewById<TextView>(R.id.date_text).text.toString()
        val ph_2 = Purchase(product_2,price_2.toDouble(),date_2)
        if(dataBaseHandler.updatePurchase(ph_1,ph_2)) {
            Toast.makeText(this, R.string.edit_zakup, Toast.LENGTH_SHORT).show()
            edit = true
        }else{
            Toast.makeText(this, R.string.error_delete, Toast.LENGTH_SHORT).show()
        }
    }


}