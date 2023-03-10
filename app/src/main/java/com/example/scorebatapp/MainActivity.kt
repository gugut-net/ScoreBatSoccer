package com.example.scorebatapp

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.scorebatapp.data.model.ResponseItemModel
import com.example.scorebatapp.data.model.VideoItemModel
import com.example.scorebatapp.databinding.ActivityMainBinding
import com.example.scorebatapp.databinding.FragmentHomeBinding
import com.example.scorebatapp.databinding.FragmentVideoBinding
import com.example.scorebatapp.ui.home.HomeAdapter
import com.example.scorebatapp.ui.login.LoginActivity
import com.example.scorebatapp.viewModel.HomeViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth


    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the RecyclerViewAdapter with the data list
        lateinit var adapter: HomeViewModel

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        /**
         * Firebase Initialized
         */
        FirebaseApp.initializeApp(applicationContext)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHost.navController

        /**
         * Passing each menu ID as a set of Ids because each
         * menu should be considered as top level destinations.
         */
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_table,
                R.id.navigation_login
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /**
         * Logout event handled
         */
        binding.btLogout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}