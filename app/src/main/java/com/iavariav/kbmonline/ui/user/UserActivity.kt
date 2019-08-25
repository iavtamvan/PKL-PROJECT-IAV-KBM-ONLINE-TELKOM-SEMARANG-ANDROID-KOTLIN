package com.iavariav.kbmonline.ui.user

import android.os.Bundle

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

import android.view.View

import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle

import android.view.MenuItem

import com.google.android.material.navigation.NavigationView
import com.iavariav.kbmonline.R
import com.iavariav.kbmonline.helper.Config
import com.iavariav.kbmonline.ui.user.fragment.HistoriUserFragment
import com.iavariav.kbmonline.ui.user.fragment.PemesananMobilFragment

import androidx.drawerlayout.widget.DrawerLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager

import android.view.Menu
import android.widget.FrameLayout

class UserActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var fragmentManager: FragmentManager? = null
    private val fmViewPagerNav: FrameLayout? = null
    private val viewpager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        fragmentManager = supportFragmentManager
        fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, PemesananMobilFragment()).commit()
        supportActionBar!!.title = "Data KBM-ONLINE"
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.user, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.pemesanan_mobil) {
            fragmentManager = supportFragmentManager
            fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, PemesananMobilFragment()).commit()
            supportActionBar!!.title = "Pemesanan Mobil"
        } else if (id == R.id.histori_pemesanan) {
            fragmentManager = supportFragmentManager
            fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, HistoriUserFragment()).commit()
            supportActionBar!!.title = "Histori Pemesanan"
        } else if (id == R.id.logout) {
            Config.logout(this@UserActivity)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
