package com.mobarak.todo.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobarak.todo.R

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    private var appBarConfiguration: AppBarConfiguration? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration.Builder(
                R.id.tasks_fragment_dest, R.id.statistics_fragment_dest)
                .build()
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController!!.addOnDestinationChangedListener { controller: NavController?, destination: NavDestination, arguments: Bundle? ->
            if (destination.id == R.id.tasks_fragment_dest || destination.id == R.id.statistics_fragment_dest) {
                navView.visibility = View.VISIBLE
            } else {
                navView.visibility = View.GONE
            }
        }
        NavigationUI.setupActionBarWithNavController(this, navController!!, appBarConfiguration!!)
        NavigationUI.setupWithNavController(navView, navController!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController!!, appBarConfiguration!!) || super.onSupportNavigateUp()
    }
}