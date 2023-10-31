package com.example.cabify

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BikeCategoriesScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bike_categories_screen)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setTitle("Bike Categories")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, DashboardScreen::class.java)
            startActivity(intent)
        }

        val images = arrayOf(
            R.drawable.img11,
            R.drawable.img12,
            R.drawable.img13
        )

        val title = arrayOf("Yamaha R15 V4","Bajaj Pulsar RS 200", "Royal Enfield Hunter")

        val seats = arrayOf("2", "2", "2")

        val price = arrayOf("210", "260", "350")

        val time = arrayOf("10-15", "20-25", "15-25")

        val arrayList = ArrayList<Items>()

        for(eachIndex in images.indices) {
            val item = Items(title[eachIndex], images[eachIndex], seats[eachIndex], price[eachIndex] , time[eachIndex])
            arrayList.add(item)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter(this, R.layout.custom_view, arrayList)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object: RecyclerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@BikeCategoriesScreen, "You clicked on item no. $position", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@BikeCategoriesScreen, ConfirmRideScreen::class.java)
                intent.putExtra("name", arrayList[position].title)
                intent.putExtra("price", arrayList[position].rate)
                intent.putExtra("time", arrayList[position].time)
                intent.putExtra("img", arrayList[position].imgId)
                startActivity(intent)
            }
        })
    }
}