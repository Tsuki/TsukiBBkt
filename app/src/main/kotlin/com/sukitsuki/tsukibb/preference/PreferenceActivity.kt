package com.sukitsuki.tsukibb.preference

import android.os.Bundle
import com.anggrayudi.materialpreference.PreferenceActivityMaterial
import com.anggrayudi.materialpreference.PreferenceFragmentMaterial
import com.sukitsuki.tsukibb.R

class PreferenceActivity : PreferenceActivityMaterial() {

  override fun onBackStackChanged() {
    preferenceFragment = supportFragmentManager.findFragmentByTag(TAG) as PreferenceFragment
    title = preferenceFragment.preferenceFragmentTitle
  }

  override fun onBuildPreferenceFragment(rootKey: String?): PreferenceFragmentMaterial {
    return PreferenceFragment.newInstance(rootKey)
  }

  private lateinit var preferenceFragment: PreferenceFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_preference)
    if (savedInstanceState == null) {
      preferenceFragment = PreferenceFragment.newInstance(null)
      supportFragmentManager.beginTransaction().add(R.id.setting_fragment, preferenceFragment, TAG)
        .commit()
    } else {
      preferenceFragment = supportFragmentManager.findFragmentByTag(TAG) as PreferenceFragment
      title = preferenceFragment.preferenceFragmentTitle
    }
  }

  companion object {
    private const val TAG = "PreferenceActivity"
  }
}