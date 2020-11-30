package net.learn2develop.moviedata_finaltask10.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import net.learn2develop.moviedata_finaltask10.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preference, rootKey);

    }
}
