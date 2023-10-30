package com.example.cabify

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "Cabify.db"
        const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create tables
        val createUserTable = """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL,
                mobile TEXT NOT NULL,
                password TEXT NOT NULL
            );
        """.trimIndent()

        val createBusTable = """
            CREATE TABLE buses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                route TEXT NOT NULL,
                seats INTEGER NOT NULL
            );
        """.trimIndent()

        val createBookingTable = """
            CREATE TABLE bookings (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                bus_id INTEGER NOT NULL,
                seat_number INTEGER NOT NULL,
                booking_date TEXT NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(id),
                FOREIGN KEY (bus_id) REFERENCES buses(id)
            );
        """.trimIndent()

        db?.execSQL(createUserTable)
        db?.execSQL(createBusTable)
        db?.execSQL(createBookingTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getUserDetails(userId: Int): User? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM users WHERE id = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(userId.toString()))

        var user: User? = null

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex("id")
            val nameIndex = cursor.getColumnIndex("name")
            val emailIndex = cursor.getColumnIndex("email")
            val mobileIndex = cursor.getColumnIndex("mobile")
            val passwordIndex = cursor.getColumnIndex("password")

            user = User(
                cursor.getInt(idIndex),
                cursor.getString(nameIndex),
                cursor.getString(emailIndex),
                cursor.getString(mobileIndex),
                cursor.getString(passwordIndex)
            )
        }

        cursor.close()
        db.close()

        return user
    }


    fun updateUserDetails(userId: Int, updatedName: String, updatedEmail: String, updatedMobile: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", updatedName)
        values.put("email", updatedEmail)
        values.put("mobile", updatedMobile)

        val updatedRows = db.update("users", values, "id = ?", arrayOf(userId.toString()))
        db.close()

        return updatedRows > 0
    }


}