package com.iavariav.kbmonline.ui.atasan

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
import com.iavariav.kbmonline.ui.atasan.fragment.AprovalFragment
import com.iavariav.kbmonline.ui.atasan.fragment.DaftarUserFragment
import com.iavariav.kbmonline.ui.atasan.fragment.DataMobilFragment
import com.iavariav.kbmonline.ui.atasan.fragment.DataUserFragment
import com.iavariav.kbmonline.ui.atasan.fragment.HistoriAtasanFragment

import androidx.drawerlayout.widget.DrawerLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager

import android.view.Menu
import android.widget.FrameLayout

class AtasanActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var fragmentManager: FragmentManager? = null
    private val fmViewPagerNav: FrameLayout? = null
    private val viewpager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        fragmentManager = supportFragmentManager
        fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, AprovalFragment()).commit()
        supportActionBar!!.title = "Data KBM-ONLINE"
    }

    fun setData() {
        fragmentManager = supportFragmentManager
        fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, AprovalFragment()).commit()
        supportActionBar!!.title = "Data KBM-ONLINE"
    }
    fun setDaftarUser() {
        fragmentManager = supportFragmentManager
        fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, DaftarUserFragment()).commit()
        supportActionBar!!.title = "Daftar User"
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
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        //        if (id == R.id.action_settings) {
        //            return true;
        //        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.data_kbm) {
            fragmentManager = supportFragmentManager
            fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, AprovalFragment()).commit()
            supportActionBar!!.title = "Data KBM-ONLINE"
        } else if (id == R.id.histori) {
            fragmentManager = supportFragmentManager
            fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, HistoriAtasanFragment()).commit()
            supportActionBar!!.title = "Histori KBM-ONLINE"
        } else if (id == R.id.daftar_user) {
            fragmentManager = supportFragmentManager
            fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, DaftarUserFragment()).commit()
            supportActionBar!!.title = "Daftar User"
        } else if (id == R.id.data_user) {
            fragmentManager = supportFragmentManager
            fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, DataUserFragment()).commit()
            supportActionBar!!.title = "Data User"
        } else if (id == R.id.data_mobil) {
            fragmentManager = supportFragmentManager
            fragmentManager!!.beginTransaction().replace(R.id.fm_view_pager_nav, DataMobilFragment()).commit()
            supportActionBar!!.title = "Data Mobil"
        } else if (id == R.id.logout) {
            Config.logout(this@AtasanActivity)
            finishAffinity()
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
