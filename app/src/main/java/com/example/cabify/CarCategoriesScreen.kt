package com.example.cabify

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarCategoriesScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_categories_screen)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setTitle("Car Categories")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, DashboardScreen::class.java)
            startActivity(intent)
        }

        val images = arrayOf(
            R.drawable.img17,
            R.drawable.img19,
            R.drawable.img18,
            R.drawable.img20
        )

        val title = arrayOf("Hyundai Aura","Honda City", "Hyundai Creta", "Toyota Fortuner")

        val seats = arrayOf("5", "5", "5", "7")

        val price = arrayOf("350", "480", "520", "780")

        val time = arrayOf("10-15", "20-25", "15-25", "20-30")

        val arrayList = ArrayList<Items>()

        for(eachIndex in images.indices) {
            val item = Items(title[eachIndex], images[eachIndex], seats[eachIndex], price[eachIndex], time[eachIndex])
            arrayList.add(item)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter(this, R.layout.custom_view, arrayList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object: RecyclerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
//                Toast.makeText(this@CarCategoriesScreen, "You clicked on item no. $position", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@CarCategoriesScreen, ConfirmRideScreen::class.java)
                intent.putExtra("name", arrayList[position].title)
                intent.putExtra("price", arrayList[position].rate)
                intent.putExtra("time", arrayList[position].time)
                intent.putExtra("img", arrayList[position].imgId)
                startActivity(intent)
            }
        })

//        val cardTrip = findViewById<CardView>(R.id.cardTrip)
//        cardTrip.setOnClickListener {
//            val intent = Intent(this, ConfirmRideScreen::class.java)
//            startActivity(intent)
//        }
    }
}