package com.example.kul.projektkoncowy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class StatisticsActivity : AppCompatActivity() {

    private val buttonEditStartDateListener = View.OnClickListener { startChooseDateActivity(1) }
    private val buttonEditEndDateListener = View.OnClickListener { startChooseDateActivity(2) }
    private val buttonResultListener = View.OnClickListener { startResultActivity() }
    private val buttonChencelListener = View.OnClickListener { mainActivity() }

    private fun mainActivity() {
        print("kostia")
        finish()
    }

    private fun startResultActivity() {
        val intent = Intent(this,ResultActivity::class.java)
        intent.putExtra("STARTDATE",findViewById<TextView>(R.id.startDate).text)
        intent.putExtra("ENDDATE",findViewById<TextView>(R.id.endDate).text)
        startActivity(intent)
    }

    private fun startChooseDateActivity(i:Int) {
        val intentWithResult = Intent(this, ChooseDateActivity::class.java)
        if(i == 1) {
            intentWithResult.putExtra("DATE", findViewById<TextView>(R.id.startDate).text)
            startActivityForResult(intentWithResult, 1)
        }else if (i == 2){
            intentWithResult.putExtra("DATE", findViewById<TextView>(R.id.endDate).text)
            startActivityForResult(intentWithResult, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                if (data != null) {
                    findViewById<TextView>(R.id.startDate).setText(data.getStringExtra("DATE"))
                }
            }
        }else if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                if (data != null) {
                    findViewById<TextView>(R.id.endDate).setText(data.getStringExtra("DATE"))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        val cal = Calendar.getInstance()
        val textStartDate = findViewById<TextView>(R.id.startDate)
        val textEndDate = findViewById<TextView>(R.id.endDate)
        if(cal.get(Calendar.MONTH) < 9) {
            textStartDate.setText("${cal.get(Calendar.YEAR)}-0${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DAY_OF_MONTH)}")
            textEndDate.setText("${cal.get(Calendar.YEAR)}-0${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DAY_OF_MONTH)}")
        }
        else{
            textStartDate.setText("${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DAY_OF_MONTH)}")
            textEndDate.setText("${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.DAY_OF_MONTH)}")
        }

        val buttonEditStartData = findViewById<Button>(R.id.edit1)
        buttonEditStartData.setOnClickListener(buttonEditStartDateListener)

        val buttonEditEndDate = findViewById<Button>(R.id.edit2)
        buttonEditEndDate.setOnClickListener(buttonEditEndDateListener)

        val buttonResult = findViewById<Button>(R.id.result)
        buttonResult.setOnClickListener(buttonResultListener)

        val buttonChencel = findViewById<Button>(R.id.cofni);
        buttonChencel.setOnClickListener(buttonChencelListener);
    }

}