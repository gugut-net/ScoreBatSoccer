package com.example.scorebatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.scorebatapp.databinding.ActivitySplashBinding
import com.example.scorebatapp.ui.login.LoginActivity
import com.example.scorebatapp.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 3000)

        setContentView(R.layout.activity_splash)

        val splashImage = findViewById<ImageView>(R.id.splash_image)
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        splashImage.startAnimation(animation)

        // Continue to main activity after animation finishes
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        })
    }

}