package com.example.cabify

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.Calendar

class ConfirmRideScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_ride_screen)

        val name = findViewById<TextView>(R.id.name)
        val price = findViewById<TextView>(R.id.price)
        val time = findViewById<TextView>(R.id.time)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val gst = findViewById<TextView>(R.id.gst)
        val total = findViewById<TextView>(R.id.total)

        val bundle: Bundle?= intent.extras

        val title = bundle!!.getString("name")
        val priceVal = bundle!!.getString("price")
        val timeVal = bundle!!.getString("time")
        val image = bundle!!.getInt("img")

        name.text = title
        price.text = priceVal
        time.text = timeVal + " min"
        imageView.setImageResource(image)

        val ridePrice = price.text.toString()
        val tax: Double = ridePrice.toDouble()*0.18
        val formattedTax = String.format("%.2f", tax)
        gst.text = "₹ " + formattedTax

        val grandTotal = tax + price.text.toString().toFloat()
        val formattedTotal = String.format("%.2f", grandTotal)
        total.text = "₹ " + formattedTotal
        price.text = "₹ "+ priceVal + " km/hr"

        val dateDialog = findViewById<EditText>(R.id.dateDialog)
        val timeDialog = findViewById<EditText>(R.id.timeDialog)
        val changeBtn = findViewById<Button>(R.id.changeBtn)
        val pickUpLocation = findViewById<EditText>(R.id.pickUpLocation)
        val dropOffLocation = findViewById<EditText>(R.id.dropOffLocation)
        val confirmBookingBtn = findViewById<Button>(R.id.confirmBookingBtn)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Ride Details")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
//            val intent = Intent(this, CarCategoriesScreen::class.java)
//            startActivity(intent)
            onBackPressed()
        }



        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        dateDialog.setOnClickListener {
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateDialog.setText("$dayOfMonth/${month+1}/$year")
            }, year, month, day)
            datePicker.show()
        }

        timeDialog.setOnClickListener {
            val timePicker = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                if(hourOfDay > 12) {
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay - 12)
                    timeDialog.setText(SimpleDateFormat(("HH:mm")).format(cal.time))
                    timeDialog.append(" PM")
                }
                else {
                    timeDialog.setText(SimpleDateFormat(("HH:mm")).format(cal.time))
                    timeDialog.append(" AM")
                }

            }
            TimePickerDialog(this, timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        }

        changeBtn.setOnClickListener {
//            val intent = Intent(this, CarCategoriesScreen::class.java)
//            startActivity(intent)
            onBackPressed()
        }

        confirmBookingBtn.setOnClickListener {
            val selectedDate = dateDialog.text.toString()
            val selectedTime = timeDialog.text.toString()
            if (selectedDate.isEmpty() || selectedTime.isEmpty() || pickUpLocation.text.isEmpty() || dropOffLocation.text.isEmpty()) {
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, OrderSummaryScreen::class.java)
                intent.putExtra("name", title)
                intent.putExtra("gst", formattedTax)
                intent.putExtra("price", ridePrice)
                intent.putExtra("img", image)
                intent.putExtra("pickUpLocation", pickUpLocation.text.toString())
                intent.putExtra("dropOffLocation", dropOffLocation.text.toString())
                intent.putExtra("date", selectedDate)
                intent.putExtra("time", selectedTime)

                startActivity(intent)
            }

        }

    }

}