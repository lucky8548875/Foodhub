package com.hilagangluzon.foodhub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hilagangluzon.foodhub.Customer.Dashboard

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val actionBar = supportActionBar
        actionBar!!.hide()


        Handler().postDelayed({

            startActivity(Intent(this, Dashboard::class.java))

        }, 1000)
    }
}
