package com.piyush.moviedbtask.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.piyush.moviedbtask.R
import com.piyush.moviedbtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.navView
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController

        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.popBackStack(navController.graph.startDestinationId, true)
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_search -> {
                    navController.popBackStack(navController.graph.startDestinationId, true)
                    navController.navigate(R.id.navigation_search)
                    true
                }
                R.id.navigation_bookmark -> {
                    navController.popBackStack(navController.graph.startDestinationId, true)
                    navController.navigate(R.id.navigation_bookmark)
                    true
                }
                else -> false
            }
        }

        bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.popBackStack(R.id.navigation_home, false)
                    navController.navigate(R.id.navigation_home)
                }
                R.id.navigation_search -> {
                    navController.popBackStack(R.id.navigation_search, false)
                    navController.navigate(R.id.navigation_search)
                }
                R.id.navigation_bookmark -> {
                    navController.popBackStack(R.id.navigation_bookmark, false)
                    navController.navigate(R.id.navigation_bookmark)
                }
            }
        }
    }
}