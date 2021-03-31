package com.codewithsouma.bookhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)

        setUpToolBar()
        startDashboardFragment()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null)
                previousMenuItem?.isChecked = false

            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.dashboard -> {
                    startFragment("Dashboard", DashboardFragment())
                    drawerLayout.closeDrawers()
                }
                R.id.favourites -> {
                    startFragment("Favourites", FavouriteFragment())
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                   startFragment("Profile", ProfileFragment())
                    drawerLayout.closeDrawers()
                }
                R.id.aboutApp -> {
                    startFragment("About App", AboutAppFragment())
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolbar)
        setTitle("Toolbar Title")
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setTitle(title: String){
        supportActionBar?.title = title
    }

    private fun startFragment(title: String, fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, fragment)
            .commit()
        setTitle(title)
    }

    private fun startDashboardFragment() {
        startFragment("Dashboard", DashboardFragment())
        val currentItem = navigationView.menu.getItem(0)
        currentItem.isChecked = true
        previousMenuItem = currentItem
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home)
            drawerLayout.openDrawer(GravityCompat.START)

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        when(supportFragmentManager.findFragmentById(R.id.frame)){
            !is DashboardFragment -> startDashboardFragment()
            else -> super.onBackPressed()
        }
    }

}