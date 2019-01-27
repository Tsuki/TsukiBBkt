package com.sukitsuki.tsukibb.preference

import android.os.Bundle
import com.anggrayudi.materialpreference.PreferenceFragmentMaterial
import com.anggrayudi.materialpreference.annotation.PreferenceKeysConfig
import com.sukitsuki.tsukibb.R

@PreferenceKeysConfig
class PreferenceFragment : PreferenceFragmentMaterial() {

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    addPreferencesFromResource(R.xml.preferences)
    findPreference(PrefKey.ABOUT)!!.summary
  }

  companion object {

    private const val TAG = "PreferenceFragment"

    fun newInstance(rootKey: String?): PreferenceFragment {
      val args = Bundle()
      args.putString(PreferenceFragmentMaterial.ARG_PREFERENCE_ROOT, rootKey)
      val fragment = PreferenceFragment()
      fragment.arguments = args
      return fragment
    }
  }
}