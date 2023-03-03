package com.example.scorebatapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scorebatapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.beginTransaction()
            .replace(R.id.loginContainer, LoginFragment())
            .commit()
    }
}