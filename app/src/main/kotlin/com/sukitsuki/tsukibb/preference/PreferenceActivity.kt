package com.sukitsuki.tsukibb.preference

import android.os.Bundle
import android.view.MenuItem
import com.sukitsuki.tsukibb.R
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber


private const val TITLE_TAG = "settingsActivityTitle"

class PreferenceActivity : DaggerAppCompatActivity() {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<PreferenceActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PreferenceActivity>()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_preference)
    if (savedInstanceState == null) {
      supportFragmentManager
        .beginTransaction()
        .replace(R.id.setting_fragment, InitPreferenceFragment())
        .commit()
    } else {
      title = savedInstanceState.getCharSequence(TITLE_TAG)
    }
    supportFragmentManager.addOnBackStackChangedListener {
      if (supportFragmentManager.backStackEntryCount == 0) {
        title = TITLE_TAG
      }
    }
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      // Use android.R.id rather than packaged id
      android.R.id.home -> {
        onBackPressed()
        return true
      }
      else -> {
        Timber.d("itemId${item?.itemId?.let { resources.getResourceName(it).split("\\/") }}")
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    // Save current activity title so we can set it again after a configuration change
    outState.putCharSequence(TITLE_TAG, title)
  }
}
