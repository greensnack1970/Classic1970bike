package com.example.gr33nsn4ck.classic1970bike;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class GeneralSettings extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

    }

}
