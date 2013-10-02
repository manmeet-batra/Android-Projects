package com.example.adwaz.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

/**
 * This is singleton class not able to create instance of this class. This class
 * is used to show ProgressDialog.
 * 
 * @author Amit
 * 
 */
public class CustomProgressDialog {
	private final static String TAG = "CustomProgressDialog";
	private static CustomProgressDialog instance;
	private static ProgressDialog dialog;

	/**
	 * This method is used to return instance if already created otherwise
	 * create new instance
	 * 
	 * @param context
	 *            {@link Context}
	 * 
	 * @return {@link CustomProgressDialog} instance
	 */
	public static CustomProgressDialog getInstance(Context context) {
		if (instance == null) {
			Log.i(TAG, "Instance Created");
			instance = new CustomProgressDialog();
		}
		dialog = new ProgressDialog(context);
		return instance;

	}

	/**
	 * This method is for stop dialog
	 */
	public void dismissDialog() {
		try {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is to show dialog pass values
	 * 
	 * @param message
	 *            message to show on progress dialog
	 * @param icon
	 *            if you want to set icon from resources then put icon here from
	 *            R.drawable.icon if not then pass 0
	 * @param title
	 *            Title that would show on the Progress Dialog
	 */
	public void showDialog(String message, int icon, String title) {
		try {
			dialog.setMessage(message);
			dialog.setTitle(title);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setIcon(icon);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
