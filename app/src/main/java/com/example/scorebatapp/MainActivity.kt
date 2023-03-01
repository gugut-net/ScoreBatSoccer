package com.example.scorebatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.scorebatapp.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint


//val logins: Map<String, String> = mapOf(
//    "test@mail.com" to "123456",
//    "admin@mail.com" to "Adm**",
//    "info@mail.com" to "SomethingSomething"
//)
//
//const val INCOMPLETE_DATA = "Please provide a valid user and password first"
//const val FAILURE_LOGIN = "Incorrect user or password"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_table
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        var user = findViewById<TextInputEditText>(R.id.tile_user).text.toString()
//        var password = findViewById<TextInputEditText>(R.id.tile_password).text.toString()
//
//        findViewById<Button>(R.id.mb_login).setOnClickListener {
//
//            if (user.isNullOrEmpty() or password.isNullOrEmpty())
//                Toast.makeText(this, INCOMPLETE_DATA, Toast.LENGTH_LONG).show() else
//            {
//                if (logins.containsKey(user).run {
//                        logins[user].equals(password)
//                    })
//                    startActivity(Intent(this,MusicContacts::class.java)).also {
//                        finish()
//                    } else {
//                    Toast.makeText(this, FAILURE_LOGIN, Toast.LENGTH_LONG).show()
//                }
//            }
//        }
    }
}