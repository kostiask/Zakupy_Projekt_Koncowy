package com.example.kul.projektkoncowy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kul.projektkoncowy.R.*
import java.util.*

class AddPurchase : AppCompatActivity(){

    private val addPurchaseListener = View.OnClickListener{addPurchase()}
    private val chooseDateListener = View.OnClickListener{startChooseDateActivity()}
    private val chanelMainActivityListener = View.OnClickListener{chanelMainActivity()}

    private fun chanelMainActivity() {
        val intent = Intent()
        intent.putExtra("CHANEL","true")
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    private fun startChooseDateActivity() {
        val intentWithResult = Intent(this, ChooseDateActivity::class.java)
        intentWithResult.putExtra("DATE",findViewById<TextView>(id.date_text).text.toString())
        startActivityForResult(intentWithResult,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                if (data != null) {
                    findViewById<TextView>(id.date_text).setText(data.getStringExtra("DATE"))
                }
            }
        }
    }

    private fun addPurchase() {
        val name = findViewById<EditText>(id.editProduct).text.toString()
        val price = findViewById<EditText>(id.edit_price).text.toString()

        val date = findViewById<TextView>(id.date_text).text.toString()
        println("DATA : $date")
        if(!name.equals("") && price.toDoubleOrNull() != null){
            findViewById<TextView>(id.error_1).visibility = View.INVISIBLE
            findViewById<TextView>(id.erorr_2).visibility = View.INVISIBLE
            val intent = Intent()
            intent.putExtra("CHANEL", "false")
            intent.putExtra("NAME_PURCHASE", name)
            intent.putExtra("PRICE_PURCHASE", price)
            intent.putExtra("DATE_PURCHASE", date)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        else if(name.equals("")){
            if(price.toDoubleOrNull() == null) {
                findViewById<TextView>(id.error_1).setText(string.errorEmptuField)
                findViewById<TextView>(id.error_1).visibility = View.VISIBLE
                findViewById<TextView>(id.erorr_2).setText(string.errorNotDouble)
                findViewById<TextView>(id.erorr_2).visibility = View.VISIBLE
            }
            else{
                findViewById<TextView>(id.erorr_2).visibility = View.INVISIBLE
                findViewById<TextView>(id.error_1).setText(string.errorEmptuField)
                findViewById<TextView>(id.error_1).visibility = View.VISIBLE
            }
        }
        else{
            findViewById<TextView>(id.error_1).visibility = View.INVISIBLE
            findViewById<TextView>(id.erorr_2).setText(string.errorNotDouble)
            findViewById<TextView>(id.erorr_2).visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_form_new_pourchase)

        val intent = getIntent()

        if(intent.getStringExtra("EDIT").toString().toBoolean()){
            findViewById<EditText>(id.editProduct).setText(intent.getStringExtra("PRODUCT"))
            findViewById<EditText>(id.edit_price).setText(intent.getStringExtra("PRICE"))
            findViewById<TextView>(id.date_text).setText(intent.getStringExtra("DATE"))
        }
        else{
            val cal = Calendar.getInstance()
            val textDate = findViewById<TextView>(id.date_text)
            if(cal.get(Calendar.MONTH) < 9 && cal.get(Calendar.DAY_OF_MONTH) < 10)
                textDate.setText("${cal.get(Calendar.YEAR)}-0${cal.get(Calendar.MONTH)+1}-0${cal.get(Calendar.DAY_OF_MONTH)}")
            else if (cal.get(Calendar.MONTH) < 9)
                textDate.setText("${cal.get(Calendar.YEAR)}-0${cal.get(Calendar.MONTH)+1}-${cal.get(Calendar.DAY_OF_MONTH)}")
            else if (cal.get(Calendar.DAY_OF_MONTH) < 10)
                textDate.setText("${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)+1}-0${cal.get(Calendar.DAY_OF_MONTH)}")
            else
                textDate.setText("${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)+1}-${cal.get(Calendar.DAY_OF_MONTH)}")
        }

        val button_save = findViewById<Button>(id.button_delete)
        button_save.setOnClickListener(addPurchaseListener)

        val buttonChoose = findViewById<Button>(id.button_edit)
        buttonChoose.setOnClickListener(chooseDateListener)

        val buttonChanel = findViewById<Button>(id.button_chanel)
        buttonChanel.setOnClickListener(chanelMainActivityListener)

    }

}