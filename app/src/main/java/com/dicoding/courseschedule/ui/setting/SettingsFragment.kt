package com.dicoding.courseschedule.ui.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var mContext: Context
    private lateinit var dailyReminder: DailyReminder

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        dailyReminder = DailyReminder()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference

        val prefNotif = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotif?.setOnPreferenceChangeListener{preference, newValue ->
            if(newValue == true){
                dailyReminder.setDailyReminder(mContext)
            }else{
                dailyReminder.cancelAlarm(mContext)
            }
            true
        }

        val prefTheme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        prefTheme?.setOnPreferenceChangeListener { preference, newValue ->
            when(newValue.toString()){
                getString(R.string.pref_dark_on) -> updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                getString(R.string.pref_dark_off) -> updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                getString(R.string.pref_dark_auto) -> updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}