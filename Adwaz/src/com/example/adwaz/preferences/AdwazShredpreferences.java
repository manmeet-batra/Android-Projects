package com.example.adwaz.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AdwazShredpreferences {

	static AdwazShredpreferences adwazSharedObj;
	static SharedPreferences prefs;
	static SharedPreferences.Editor prefsEditor;
	static final String TAG = "ADWAZ";

	private AdwazShredpreferences() {

	}

	public AdwazShredpreferences(Context ctx) {

		if (adwazSharedObj == null) {
			adwazSharedObj = new AdwazShredpreferences();
			SetSharedPreference(ctx);
			setPrefsEditor(getPrefs());
		}
	}

	public void SetSharedPreference(Context context) {

		prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);

	}

	public void setPrefsEditor(SharedPreferences prefsEditor) {
		AdwazShredpreferences.prefsEditor = prefsEditor.edit();

	}

	public SharedPreferences getPrefs() {
		return prefs;
	}

	public SharedPreferences.Editor getPrefsEditor() {
		return prefsEditor;
	}
}
