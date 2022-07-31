package com.example.medicalrehabilitation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val beginbutton: ImageButton = findViewById(R.id.begin_button)

        beginbutton.setOnClickListener {
            val intent = Intent(this@MainActivity, Training::class.java)
            startActivity(intent)
        }
    }
}