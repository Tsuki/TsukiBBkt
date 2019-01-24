package com.sukitsuki.tsukibb.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.material.navigation.NavigationView
import com.sukitsuki.tsukibb.R
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<MainActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
  }

  @Inject
  lateinit var playerNotificationManager: PlayerNotificationManager
  @Inject
  fun logInjection() {
    Log.d(this::class.java.simpleName, "Injecting ${this::class.java.simpleName}")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    setContentView(R.layout.activity_main)
    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    val navView: NavigationView = findViewById(R.id.nav_view)
    val toggle = ActionBarDrawerToggle(
      this, drawerLayout, toolbar,
      R.string.navigation_drawer_open,
      R.string.navigation_drawer_close
    )
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    navView.setNavigationItemSelectedListener(this)

  }

  override fun onBackPressed() {
    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
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
    return when (item.itemId) {
      R.id.action_settings -> {
        val intent = Intent(this, LoginWebViewActivity::class.java)
        this.startActivity(intent)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
      R.id.nav_home -> {
      }
      R.id.nav_gallery -> {
      }
      R.id.nav_favorite -> {
      }
      R.id.nav_history -> {
      }
      R.id.nav_login -> {
        this.startActivity(Intent(this, LoginWebViewActivity::class.java))
      }
      R.id.nav_logout -> {
      }
      R.id.nav_download -> {
      }
      R.id.nav_setting -> {
      }
      else -> {
        return false
      }
    }
    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }

  override fun onDestroy() {
    super.onDestroy()
    playerNotificationManager.setPlayer(null)
  }
}
