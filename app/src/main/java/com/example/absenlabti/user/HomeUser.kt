package com.example.absenlabti.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.absenlabti.R
import kotlinx.android.synthetic.main.activity_home_user.*

class HomeUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user)

        imgAbsen.setOnClickListener{
            startActivity(Intent(this, Absen::class.java))
            finish()
        }
    }

}