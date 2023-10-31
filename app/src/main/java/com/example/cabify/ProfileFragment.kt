package com.example.cabify

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class ProfileFragment : Fragment() {
    private lateinit var emailEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var mobileEditText: EditText
    private lateinit var editProfileButton: Button
    private lateinit var myLocation: TextView
    private lateinit var logoutButton: Button

    private var inEditMode = false
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        emailEditText = view.findViewById(R.id.emailEditText)
        nameEditText = view.findViewById(R.id.nameEditText)
        mobileEditText = view.findViewById(R.id.mobileEditText)
        editProfileButton = view.findViewById(R.id.editProfileButton)
        myLocation = view.findViewById(R.id.myLocation)
        logoutButton = view.findViewById(R.id.logoutButton)

        val userId = 1
        val dbHelper = DatabaseHelper(requireContext())
        val user = dbHelper.getUserDetails(userId)

        if (user != null) {
            nameEditText.setText(user.name)
            emailEditText.setText(user.email)
            mobileEditText.setText(user.mobile)
        } else {
            Toast.makeText(requireContext(), "Failed to load user details", Toast.LENGTH_SHORT).show()
        }

        editProfileButton.setOnClickListener {
            if (inEditMode) {
                val updatedName = nameEditText.text.toString()
                val updatedEmail = emailEditText.text.toString()
                val updatedMobile = mobileEditText.text.toString()

                val success = dbHelper.updateUserDetails(userId, updatedName, updatedEmail, updatedMobile)

                if (success) {
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                }

                editProfileButton.text = "Edit Profile"
                inEditMode = false
                nameEditText.isEnabled = false
                emailEditText.isEnabled = false
                mobileEditText.isEnabled = false
            } else {
                editProfileButton.text = "Save"
                inEditMode = true
                nameEditText.isEnabled = true
                emailEditText.isEnabled = true
                mobileEditText.isEnabled = true
            }
        }

        logoutButton.setOnClickListener {
            exitAlert()
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


        getLocation() // Call this method to get the location


        return view
    }

    fun exitAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to logout ?")
        builder.setTitle("Logout Alert!")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") {
                dialog, which -> logoutRedirect()
        }
        builder.setNegativeButton("No") {
                dialog, which -> dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun logoutRedirect() {
        val intent = Intent(requireActivity(), LoginScreen::class.java)
        startActivity(intent)
    }

    private fun getLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: List<Address>?
                        list = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        if (list != null) {
                            if (list.isNotEmpty()) {
                                myLocation.text = "${list[0].getAddressLine(0)}"
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), permissionId)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }


}
