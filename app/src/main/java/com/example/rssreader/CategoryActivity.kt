package com.example.rssreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val intent = Intent( applicationContext, MainActivity::class.java )
        val btnSports = findViewById<Button>(R.id.btnSports)
        btnSports.setOnClickListener {
            intent.putExtra("CATEGORY", btnSports.text.toString())
            startActivity(intent)
        }
        val btnEntertainment = findViewById<Button>(R.id.btnEntertainment)
        btnEntertainment.setOnClickListener {
            intent.putExtra("CATEGORY", btnEntertainment.text.toString())
            startActivity(intent)
        }
        val btnBusiness = findViewById<Button>(R.id.btnBusiness)
        btnBusiness.setOnClickListener {
            intent.putExtra("CATEGORY", btnBusiness.text.toString())
            startActivity(intent)
        }


    }
}