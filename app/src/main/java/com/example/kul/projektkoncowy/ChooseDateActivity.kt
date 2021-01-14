package com.example.kul.projektkoncowy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import java.util.*

class ChooseDateActivity : AppCompatActivity() {

    private val buttonOkClickListener = View.OnClickListener{onClick()}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choosedate)
        val intent = getIntent()
        val textDate = findViewById<TextView>(R.id.dateText)
        textDate.setText(intent.getStringExtra("DATE"))
        val calendar = findViewById<CalendarView>(R.id.calendar)
        calendar.setOnDateChangeListener{view, year, month, dayOfMonth ->
            if(month < 9 && dayOfMonth < 10)textDate.setText("$year-0${month+1}-0$dayOfMonth")
            else if(month < 9)textDate.setText("$year-0${month+1}-$dayOfMonth")
            else if(dayOfMonth < 10)textDate.setText("$year-${month+1}-0$dayOfMonth")
            else textDate.setText("$year-${month+1}-$dayOfMonth")}
        val buttonOk = findViewById<Button>(R.id.buttonOk)
        buttonOk.setOnClickListener(buttonOkClickListener)
    }

    private fun onClick(){

        var date = findViewById<TextView>(R.id.dateText).text.toString()
        val intent = Intent()
        intent.putExtra("DATE", date)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}