package com.example.cabify

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScootyCategoriesScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scooty_category_screen)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setTitle("Scooty Categories")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, DashboardScreen::class.java)
            startActivity(intent)
        }

        val images = arrayOf(
            R.drawable.img14,
            R.drawable.img15,
            R.drawable.img16
        )

        val title = arrayOf("Sedan", "Sedan", "Sedan")

        val seats = arrayOf("2 seats", "2 seats", "2 seats")

        val price = arrayOf("210", "150", "50")

        val time = arrayOf("10-15", "25-30", "10-20")

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
//                Toast.makeText(this@ScootyCategoriesScreen, "You clicked on item no. $position", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ScootyCategoriesScreen, ConfirmRideScreen::class.java)
                intent.putExtra("name", arrayList[position].title)
                intent.putExtra("price", arrayList[position].rate)
                intent.putExtra("time", arrayList[position].time)
                intent.putExtra("img", arrayList[position].imgId)
                startActivity(intent)
            }
        })
    }
}