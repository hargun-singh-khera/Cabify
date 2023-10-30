package com.example.cabify

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext

class OrderSummaryScreen : AppCompatActivity() {
    val CHANNEL_ID = "channelId"
    val CHANNEL_NAME = "channelName"
    val notificationId = 0
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_summary_screen)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setTitle("Order Summary")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
//            val intent = Intent(this, ConfirmRideScreen::class.java)
//            startActivity(intent)
            onBackPressed()
        }

        val vechicleName = findViewById<TextView>(R.id.vechicleName)
        val subTotal = findViewById<TextView>(R.id.subTotal)
        val gstLabel = findViewById<TextView>(R.id.gst)
        val platformFee = findViewById<TextView>(R.id.platformFee)
        val grandTotal = findViewById<TextView>(R.id.grandTotal)
        val imgCar = findViewById<ImageView>(R.id.imgCar)
        val pickUpLocation = findViewById<TextView>(R.id.pickUpLocation)
        val pickUpTime = findViewById<TextView>(R.id.pickUpTime)
        val dropOffLocation = findViewById<TextView>(R.id.dropOffLocation)
        val travelDate = findViewById<TextView>(R.id.travelDate)
        val bookMyRide = findViewById<Button>(R.id.bookMyRide)

        val name = intent.getStringExtra("name")
        val gst = intent.getStringExtra("gst")
        val price = intent.getStringExtra("price")
        val img = intent.getIntExtra("img", 0)
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")
        val pickLocation = intent.getStringExtra("pickUpLocation")
        val dropLocation = intent.getStringExtra("dropOffLocation")

        Toast.makeText(this, "Price: $price", Toast.LENGTH_SHORT).show()


        vechicleName.text = name
        imgCar.setImageResource(img)
        pickUpLocation.text = pickLocation
        dropOffLocation.text = dropLocation
        pickUpTime.text = time
        travelDate.text = date

        subTotal.text = "₹ "+ String.format("%.2f", price.toString().toDouble())
        gstLabel.text = "₹ "+ gst
        val fee = platformFee.text.toString()
        Toast.makeText(this, "Fee $fee", Toast.LENGTH_SHORT).show()
        val total = price.toString().toDouble() + gst.toString().toDouble() + 2
        grandTotal.text = "₹ "+ String.format("%.2f", total)

        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            // using notification anatomy
            .setContentTitle("Ride Booking Confirmation")
            .setContentText("Dear User, \nYour Ride has been booked successfully, and our cab driver will contact you shortly. \nCab details: \nVechicle Name: $name \nPick up Location: $pickLocation \nDate of travel: ${travelDate.text.toString()} \nAmount to be paid: ₹ ${
                String.format(
                    "%.2f",
                    total
                )
            }. \nThanks for choosing Cabify.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        bookMyRide.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            notificationManager.notify(notificationId, notification)
        }

    }

    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}