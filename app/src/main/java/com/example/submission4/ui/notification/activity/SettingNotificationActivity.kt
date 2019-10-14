package com.example.submission4.ui.notification.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.os.ResultReceiver
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.submission4.R
import com.example.submission4.ui.notification.daily.DailyReminder
import com.example.submission4.ui.notification.releasetoday.ReleaseTodayReminder
import kotlinx.android.synthetic.main.activity_main.*

class SettingNotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                finish()
                true
            }
        }
    }

    class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
        private val dailyReminderPreference: SwitchPreference by lazy { findPreference<SwitchPreference>(keyDaily)!! }
        private val releaseReminderPreference: SwitchPreference by lazy { findPreference<SwitchPreference>(keyRelease)!! }

        private val keyDaily by lazy { getString(R.string.general_setting_notification_reminder_open_apps_key) }
        private val keyRelease by lazy { getString(R.string.general_setting_notification_reminder_release_today_key) }

        private val dailyReminder: DailyReminder by lazy { DailyReminder() }
        private val releaseToday: ReleaseTodayReminder by lazy { ReleaseTodayReminder() }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings_notification_preference, rootKey)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            setUpPreference()
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        private fun setUpPreference() {
            val sharedPreference = preferenceManager.sharedPreferences
            dailyReminderPreference.isChecked = sharedPreference.getBoolean(keyDaily, false)
            releaseReminderPreference.isChecked = sharedPreference.getBoolean(keyRelease, false)
        }

        override fun onSharedPreferenceChanged(sp: SharedPreferences?, key: String?) {
            key?.let {
                if (it == keyDaily) {
                    dailyReminderPreference.isChecked = sp?.getBoolean(keyDaily, false)!!
                    if(dailyReminderPreference.isChecked) dailyReminder.setDailyReminder(requireContext())
                    else dailyReminder.cancelReminder(requireContext())
                }

                if (it == keyRelease) {
                    releaseReminderPreference.isChecked = sp?.getBoolean(keyRelease, false)!!
                    if (releaseReminderPreference.isChecked) releaseToday.setReleaseReminderToday(requireContext())
                    else releaseToday.cancelReleaseReminderToday(requireContext())
                }
            }
        }
    }
}