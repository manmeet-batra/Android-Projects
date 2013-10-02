package com.example.adwaz.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import com.example.adwaz.R;
import com.example.adwaz.SplashActivity;

/**
 * This class used to define methods that are used for the multiple
 * functionality and for multiple classes commonly
 */
public class UtilityMethods {

	/**
	 * Functionality to start activity or new screen within the application
	 * 
	 * @param Activity
	 *            context
	 * @param activity
	 *            id that is defined in Constants class
	 * @param true if need to set flag (Intent.FLAG_ACTIVITY_CLEAR_TASK |
	 *        Intent.FLAG_ACTIVITY_NEW_TASK))
	 */
	public static void startActivity(Context context, int activityId,
			boolean isFlagRequired) {
		Class newActivity = null;
		switch (activityId) {

		default:
			break;

		}

		// finally start activity
		if (newActivity != null) {
			if (isFlagRequired) {
				context.startActivity(new Intent(context, newActivity)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK));
			} else {
				context.startActivity(new Intent(context, newActivity));
			}
		}
	}

	/**
	 * Functionality to replace fragment according to requirement
	 * 
	 * @param fragmentClassObj
	 *            fragment activity context that need to open
	 * @param context
	 *            current fragment Class object
	 * @param fragmentId
	 *            current fragment id that need to be replaced with new fragment
	 *            class
	 * @param isAnimationRequired
	 *            true if need to animate
	 * @param isBackstackRequired
	 *            true if need to add on back stack of fragment manager
	 * @param fragmentTag
	 *            tag name for the fragment
	 * @param bundle
	 *            if bundle need to pass through the fragment
	 */
	public static synchronized void replaceFragment(Fragment fragmentClassObj,
			FragmentActivity context, int fragmentId,
			boolean isAnimationRequired, boolean isBackstackRequired,
			String fragmentTag, Bundle bundle) {
		try {
			if (fragmentClassObj != null && context != null && fragmentId > 0) {
				// SignUpFragment farg = new SignUpFragment();
				FragmentTransaction fragmentTransaction = context
						.getSupportFragmentManager().beginTransaction();

				if (bundle != null) {
					fragmentClassObj.setArguments(bundle);
				}

				if (fragmentTag != null && !fragmentTag.equals("")
						&& !fragmentTag.equalsIgnoreCase("null")) {
					fragmentTransaction.replace(fragmentId, fragmentClassObj,
							fragmentTag);
				} else {
					fragmentTransaction.replace(fragmentId, fragmentClassObj);
				}

				// animation during fragment replacement
				if (isAnimationRequired) {
					fragmentTransaction.setCustomAnimations(
							android.R.anim.fade_in, android.R.anim.fade_out,
							android.R.anim.fade_in, android.R.anim.fade_out);
				}

				// add fragment to back stack
				if (isBackstackRequired) {
					fragmentTransaction.addToBackStack(null);
				}

				fragmentTransaction.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to add fragment according to requirement for the current
	 * layout
	 * 
	 * @param fragmentClassObj
	 *            fragment activity context that need to open
	 * @param context
	 *            current fragment Class object
	 * @param fragmentId
	 *            current fragment id that need to be replaced with new fragment
	 *            class
	 * @param isAnimationRequired
	 *            true if need to animate
	 * @param isBackstackRequired
	 *            true if need to add on back stack of fragment manager
	 * @param fragmentTag
	 *            tag name for the fragment
	 * @param bundle
	 *            if bundle need to pass through the fragment
	 */
	public static void addFragment(Fragment fragmentClassObj,
			FragmentActivity context, int fragmentId,
			boolean isAnimationRequired, boolean isBackstackRequired,
			String fragmentTag, Bundle bundle) {
		try {
			if (fragmentClassObj != null && context != null && fragmentId > 0) {
				// SignUpFragment farg = new SignUpFragment();
				FragmentTransaction fragmentTransaction = context
						.getSupportFragmentManager().beginTransaction();

				if (bundle != null) {
					fragmentClassObj.setArguments(bundle);
				}

				if (fragmentTag != null && !fragmentTag.equals("")
						&& !fragmentTag.equalsIgnoreCase("null")) {
					fragmentTransaction.add(fragmentId, fragmentClassObj,
							fragmentTag);
				} else {
					fragmentTransaction.add(fragmentId, fragmentClassObj);
				}

				// animation during fragment replacement
				if (isAnimationRequired) {
					fragmentTransaction.setCustomAnimations(
							android.R.anim.fade_in, android.R.anim.fade_out,
							android.R.anim.fade_in, android.R.anim.fade_out);
				}

				// add fragment to back stack
				if (isBackstackRequired) {
					fragmentTransaction.addToBackStack(null);
				}

				fragmentTransaction.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Functionality to check valid email address through a defined pattern
	 */
	private static Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

	public static boolean checkForValidEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	

	/**
	 * Functionality to check the Internet availability
	 * 
	 * @param context
	 *            Activity context
	 * 
	 * @return true if Internet is connected
	 */
	public static boolean isInternetConnected(Context context) {
		boolean isWifiConnected = false;
		boolean isMobileInternetConnected = false;
		try {

			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo[] netInfo = cm.getAllNetworkInfo();
			for (NetworkInfo ni : netInfo) {
				if (ni.getTypeName().equalsIgnoreCase("WIFI"))
					if (ni.isConnected())
						isWifiConnected = true;
				if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
					if (ni.isConnected())
						isMobileInternetConnected = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isWifiConnected || isMobileInternetConnected;
	}

	/**
	 * Functionality to parse json for user id after login. This userId is saved
	 * for further use in the application.
	 * 
	 * @param context
	 *            FragmentActivity context
	 * @param response
	 *            server response
	 * @param taskIdErrorMsg
	 *            if any task id that will be associated for button click event
	 *            listener for the respective process for error message that is
	 *            fetched from server
	 * 
	 * @return true if userId successfully parsed
	 */
	public static String isUserIdParsed(FragmentActivity context,
			String response, int taskIdErrorMsg) {
		String msgToPass = "";
		try {
			JSONObject json = new JSONObject(response);
			String responseString = "";
			if (json.has("response")) {
				responseString = json.getString("response");

				if (responseString.equalsIgnoreCase("true")) {

					if (json.has("responseData")) {
						JSONObject responseDataJson = json
								.getJSONObject("responseData");

						String userId = "";

						if (responseDataJson.has("User")) {
							JSONObject userJson = responseDataJson
									.getJSONObject("User");

							// this will be the user Id as discussed with Munish
							// Sir
							if (userJson.has("id")) {
								// userData.setId(userJson.getString("id"));
								userId = userJson.getString("id");
							}

						}

						if (responseDataJson.has("UserDetail")) {
							JSONObject userDetailJson = responseDataJson
									.getJSONObject("UserDetail");

							// this is a foreign key as per discussed with
							// Munish Sir. It'll be same as 'id' key in the
							// responseData json packet as defined above
							if (userId == null || userId.equals("")
									|| userId.equalsIgnoreCase("null")) {
								if (userDetailJson.has("user_id")) {

									userId = userDetailJson
											.getString("user_id");

								}
							}

						}

						if (userId != null && !userId.equals("")
								&& !userId.equalsIgnoreCase("null")) {
						}

					} else {
						if (json.has("msg")) {

						}
					}

				} 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return msgToPass;
	}

	/**
	 * Functionality to finish current activity
	 */
	public static void finishCurrentActivity(FragmentActivity context) {
		context.finish();
	}


	/**
	 * Functionality to hide soft keyboard of the device
	 * 
	 * @param context
	 *            Fragment activity object
	 */
	public static void hideKeyboard(FragmentActivity context) {
		try {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Activity.INPUT_METHOD_SERVICE);
			/* imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); */
			imm.hideSoftInputFromWindow(context.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to create uri from bitmap
	 * 
	 * @param context
	 *            Activity context
	 * @param bitmap
	 *            bitmap that need to create uri from
	 * @param imageTitle
	 *            the title for the image/bitmap
	 * 
	 * @return Uri for the required bitmap
	 */
	public Uri getImageUri(Context context, Bitmap bitmap, String imageTitle) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(context.getContentResolver(),
				bitmap, imageTitle, null);
		return Uri.parse(path);
	}

	
	/**
	 * Functionality to create a file from bitmap
	 * 
	 * @param context
	 *            Activity context
	 * @param bitmap
	 *            image bitmap
	 * @param fileName
	 *            name of the file
	 * */
	public static File createFileBitmap(Context context, Bitmap bitmap,
			String fileName) {
		File f = null;
		try {
			File rootSdDirectory = Environment.getExternalStorageDirectory();
			f = new File(rootSdDirectory, fileName + ".jpg");
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();

			// Convert bitmap to byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100 /* ignored for PNG */, bos);
			byte[] bitmapdata = bos.toByteArray();

			// write the bytes in file
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
			bitmap = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}
