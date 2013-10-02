package com.example.adwaz.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author user This is SharedPreferences SingleTon Class used to store small
 *         amount of data in private mode
 * 
 */
public class Sharedprefrences {
	private static Sharedprefrences instance;
	private static SharedPreferences pref;
	private static SharedPreferences.Editor editor;
	private final static String name = "baseUtil";

	/**
	 * 
	 * @param mcontext
	 *            Parameterized Constructor is called
	 */
	public static Sharedprefrences getInstance(Context context) {
		if (instance == null) {
			instance = new Sharedprefrences();
			pref = context.getSharedPreferences(name, 0);
			editor = pref.edit();
		}
		return instance;
	}

	/**
	 * Default Constructor is called
	 */
	private Sharedprefrences() {

	}

	/**
	 * Here all values is to put into shared prefrences
	 * 
	 * @param key
	 *            Unique Key for a value
	 * @param value
	 *            Value to be stored
	 */
	public void putboolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void putsharedstring(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	public void putsharedint(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public void putlong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public void putfloat(String key, float value) {
		editor.putFloat(key, value);
		editor.commit();
	}

	public void remove(String key) {
		editor.remove(key);
		editor.commit();
	}

	public void clearPref() {
		editor.clear();
		editor.commit();
	}

	/**
	 * Here all values is to get String value from shared preferences
	 * 
	 * @param key
	 *            retrieve by unique key
	 * @param defaultvalue
	 *            give here defaultValue if not found defalutValue is assigned
	 * @return string
	 */
	public String getsharedstring(String key, String defaultvalue) {
		return pref.getString(key, defaultvalue);
	}

	/**
	 * Here all values is to get int value from shared preferences
	 * 
	 * @param key
	 *            retrieve by unique key
	 * @param defaultvalue
	 *            give here defaultValue if not found defalutValue is assigned
	 * @return int
	 */
	public int getsharedint(String key, int defValue) {
		return pref.getInt(key, defValue);
	}

	/**
	 * Here all values is to get boolean value from shared preferences
	 * 
	 * @param key
	 *            retrieve by unique key
	 * @param defaultvalue
	 *            give here defaultValue if not found defalutValue is assigned
	 * @return boolean
	 */
	public boolean getboolean(String key, boolean defValue) {
		return pref.getBoolean(key, defValue);
	}

	/**
	 * Here all values is to get long valued from shared preferences
	 * 
	 * @param key
	 *            retrieve by unique key
	 * @param defaultvalue
	 *            give here defaultValue if not found defalutValue is assigned
	 * @return long
	 */
	public long getlong(String key, long defValue) {
		return pref.getLong(key, defValue);
	}

	/**
	 * Here all values is to get float value from shared preferences
	 * 
	 * @param key
	 *            retrieve by unique key
	 * @param defaultvalue
	 *            give here defaultValue if not found defalutValue is assigned
	 * @return float
	 */
	public float getfloat(String key, float defValue) {
		return pref.getFloat(key, defValue);
	}

}
