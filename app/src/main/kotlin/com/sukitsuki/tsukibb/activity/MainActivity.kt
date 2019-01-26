package com.sukitsuki.tsukibb.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.material.navigation.NavigationView
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.preference.PreferenceActivity
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<MainActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
  }

  @Inject
  lateinit var exoPlayer: SimpleExoPlayer
  @Inject
  lateinit var playerNotificationManager: PlayerNotificationManager
  @BindView(R.id.toolbar)
  lateinit var toolbar: Toolbar
  @BindView(R.id.drawer_layout)
  lateinit var drawerLayout: DrawerLayout
  @BindView(R.id.nav_view)
  lateinit var navView: NavigationView

  private lateinit var loginDialog: AlertDialog

  @Inject
  fun logInjection() {
    Timber.d("Injecting ${this::class.java.simpleName}")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    setContentView(R.layout.activity_main)
    ButterKnife.bind(this)
    isLogged(false)
    setSupportActionBar(toolbar)
    val toggle = ActionBarDrawerToggle(
      this, drawerLayout, toolbar,
      R.string.navigation_drawer_open,
      R.string.navigation_drawer_close
    )
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    navView.setNavigationItemSelectedListener(this)
    loginDialog = AlertDialog.Builder(this@MainActivity)
      .setItems(arrayOf("Google", "Telegram")) { _, i ->
        // Must use putExtras rather than 3nd arg, then bundle is activity options not extras
        if (i == 0) {
          Toast.makeText(this@MainActivity.applicationContext, "Not support now", Toast.LENGTH_LONG).show()
          return@setItems
        }
        this.startActivity(Intent(this, LoginWebViewActivity::class.java)
          .apply { this.putExtras(bundleOf("login" to i)) })
      }
      .setTitle("Login with:").create()

  }

  override fun onBackPressed() {
    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
      R.id.nav_home -> {
      }
      R.id.nav_favorite -> {
      }
      R.id.nav_history -> {
      }
      R.id.nav_login -> {
        loginDialog.show()
      }
      R.id.nav_logout -> {
      }
      R.id.nav_download -> {
      }
      R.id.nav_setting -> {
        this.startActivity(Intent(this, PreferenceActivity::class.java))
      }
      else -> {
        return false
      }
    }
    drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }

  private fun isLogged(boolean: Boolean) {
    if (boolean) {
      navView.menu.findItem(R.id.nav_login).isVisible = false
      navView.menu.findItem(R.id.nav_logout).isVisible = true
    } else {
      navView.menu.findItem(R.id.nav_login).isVisible = true
      navView.menu.findItem(R.id.nav_logout).isVisible = false
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    isLogged(false)
  }

  override fun onDestroy() {
    super.onDestroy()
    playerNotificationManager.setPlayer(null)
    exoPlayer.release()
  }
}
