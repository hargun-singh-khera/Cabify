package com.example.cabify

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView


class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val bikeLayout = view.findViewById<CardView>(R.id.bikeLayout)
        val scootyLayout = view.findViewById<CardView>(R.id.scootyLayout)
        val carLayout = view.findViewById<CardView>(R.id.carLayout)
        val rideBtn = view.findViewById<Button>(R.id.rideBtn)
        bikeLayout.setOnClickListener {
            val intent = Intent(it.context, BikeCategoriesScreen::class.java)
            startActivity(intent)
        }

        scootyLayout.setOnClickListener {
            val intent = Intent(it.context, ScootyCategoriesScreen::class.java)
            startActivity(intent)
        }

        carLayout.setOnClickListener {
            val intent = Intent(it.context, CarCategoriesScreen::class.java)
            startActivity(intent)
        }

        rideBtn.setOnClickListener {
            Toast.makeText(it.context, "Choose your ride from any categories", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}