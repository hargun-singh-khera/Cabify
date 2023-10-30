package com.example.cabify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ProfileFragment : Fragment() {
    private lateinit var emailEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var mobileEditText: EditText
    private lateinit var editProfileButton: Button

    private var inEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        emailEditText = view.findViewById(R.id.emailEditText)
        nameEditText = view.findViewById(R.id.nameEditText)
        mobileEditText = view.findViewById(R.id.mobileEditText)
        editProfileButton = view.findViewById(R.id.editProfileButton)


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

        return view
    }
}
