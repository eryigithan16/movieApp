package com.yigithan.movieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.yigithan.movieapp.R
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val appName = textMovieAppSplash
        appName.animate().translationX(1400F).setDuration(2700).setStartDelay(1000)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this,MainActivity::class.java))
        }, 2300)
    }
}