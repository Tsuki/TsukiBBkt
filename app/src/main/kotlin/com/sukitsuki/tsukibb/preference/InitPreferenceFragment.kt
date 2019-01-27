package com.sukitsuki.tsukibb.preference

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.sukitsuki.tsukibb.R

class InitPreferenceFragment : PreferenceFragmentCompat() {
  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.preferences, rootKey)
  }
}