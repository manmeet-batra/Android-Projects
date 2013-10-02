package com.example.adwaz.alerts;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Functionality to view toast message
 */
public class ViewToastMsg {
	/**
	 * @param Activity
	 *            context
	 * @param message
	 *            to be displayed
	 */
	public ViewToastMsg(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}
