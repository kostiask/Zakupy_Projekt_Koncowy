package com.example.kul.projektkoncowy

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ResultActivity : AppCompatActivity() {

    private val buttonListener = View.OnClickListener { backStatisticActivity() }

    private fun backStatisticActivity() {
        finish()
    }

    private var list_purchas = listOf<Purchase>()
    private var startDate : LocalDate? = null
    private var endDate : LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val intent = getIntent()
        val startEndDate = findViewById<TextView>(R.id.startEndDate)
        startEndDate.setText(intent.getStringExtra("STARTDATE") + "  -  " + intent.getStringExtra("ENDDATE"))

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        startDate = LocalDate.parse(intent.getStringExtra("STARTDATE"),formatter)
        endDate = LocalDate.parse(intent.getStringExtra("ENDDATE"),formatter)

        val button = findViewById<Button>(R.id.nazad);
        button.setOnClickListener(buttonListener)

        getListPurchas()
        countAll()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getListPurchas() {
        val dataBaseHandler = DataBaseHelper(this,null,null,1)
        list_purchas = dataBaseHandler.all()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun countAll(){
        var numberDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        var count : Double = 0.0
        var min : Double = 0.0
        var max : Double = 0.0
        var maxDate : LocalDate? = null
        var minDate : LocalDate? = null
        var list_temp = mutableListOf<Purchase>()
        if(!list_purchas.isEmpty()){



            for (i in 0..list_purchas.size-1) {
                if(!list_purchas[i].date?.isAfter(endDate)!!){
                    if(!list_purchas[i].date?.isBefore(startDate)!!){
                        list_temp.add(list_purchas[i])
                    }
                }
            }

            if(!list_temp.isEmpty()) {

                min = list_purchas[0].price!!
                max = list_purchas[0].price!!
                minDate = list_purchas[0].date!!
                maxDate = list_purchas[0].date!!

                for (i in 0..list_temp.size - 1) {
                    count += list_purchas[i].price!!
                    if (list_purchas[i].price!! < min) {
                        min = list_purchas[i].price!!
                        minDate = list_purchas[i].date!!
                    }
                    if (list_purchas[i].price!! > max) {
                        max = list_purchas[i].price!!
                        maxDate = list_purchas[i].date!!
                    }
                }
            }
        }

        findViewById<TextView>(R.id.all_day).setText(count.toString() + "zł")
        findViewById<TextView>(R.id.srednia).setText((count/numberDays).toString() + "zł")
        if(minDate == null){
            findViewById<TextView>(R.id.maxM).setText("${max}zł")
            findViewById<TextView>(R.id.minM).setText("${min}zł")
        }else{
            findViewById<TextView>(R.id.maxM).setText("${max}zł - $maxDate")
            findViewById<TextView>(R.id.minM).setText("${min}zł - $minDate")
        }

    }



}