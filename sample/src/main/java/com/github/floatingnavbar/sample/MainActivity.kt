package com.github.floatingnavbar.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.floatingnavbar.FloatingBottomNavBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavBar = findViewById<FloatingBottomNavBar>(R.id.bottomNavBar)

        val items = listOf(
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_today, "Home"),
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_view, "Insights"),
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_camera, "Profile"),
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_compass, "Discover"),
            FloatingBottomNavBar.NavBarItem(android.R.drawable.ic_menu_search, "Search")
        )

        bottomNavBar.setItems(items)

        bottomNavBar.onItemSelectedListener = { index ->
            Toast.makeText(this, "Selected: ${items[index].title}", Toast.LENGTH_SHORT).show()
        }
    }
}
