package com.example.adwaz.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.example.adwaz.R;
import com.example.adwaz.abstractclasses.AbstractFragmentActivity;
import com.example.adwaz.alerts.AlertDialogUtil;
import com.example.adwaz.listeners.OnAlertDialogFragmentListener;

public class FragmentsUtilClass {

	/**
	 * This method is used to clear all the fragments from stack
	 */
	public static void clearBackStack(FragmentActivity context) {
		try {

			FragmentManager fm = context.getSupportFragmentManager();
			for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
				String fragTag = fm.getBackStackEntryAt(i).getName();
				Fragment fragment = fm.findFragmentByTag(fragTag);
				FragmentTransaction fragTrans = context
						.getSupportFragmentManager().beginTransaction();
				fragTrans.remove(fragment);
				fragTrans.commit();
				fm.popBackStack();

			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to replace fragment according to requirement
	 * 
	 * @param fragment
	 *            activity context
	 * @param Class
	 *            object
	 * @param current
	 *            fragment id that need to be replaced with new fragment class
	 * @param true if need to animate
	 * @param true if need to add on back stack of fragment manager
	 * @param tag
	 *            name for the fragment
	 */
	public static void replaceFragment(
			AbstractFragmentActivity fragmentClassObj,
			FragmentActivity context, int fragmentId,
			boolean isAnimationRequired, boolean isBackstackRequired,
			String fragmentTag, Bundle fragBundle) {
		try {
			if (fragmentClassObj != null && context != null && fragmentId > 0) {
				// SignUpFragment farg = new SignUpFragment();
				FragmentTransaction fragmentTransaction = context
						.getSupportFragmentManager().beginTransaction();

				if (fragmentTag != null && !fragmentTag.equals("")
						&& !fragmentTag.equalsIgnoreCase("null")) {
					/*
					 * Fragment mFragment = context.getSupportFragmentManager()
					 * .findFragmentById(fragmentId); if (mFragment != null) {
					 * 
					 * FragmentTransaction ft = context
					 * .getSupportFragmentManager().beginTransaction();
					 * ft.detach(mFragment);
					 * 
					 * ft.commit(); Log.v("REMOVED", mFragment.getTag()); }
					 */
					if (fragBundle != null) {
						fragmentClassObj.setArguments(fragBundle);
					}
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
					fragmentTransaction.addToBackStack(fragmentTag);
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
	 * @param fragment
	 *            activity context
	 * @param Class
	 *            object
	 * @param current
	 *            fragment id that need to be replaced with new fragment class
	 * @param true if need to animate
	 * @param true if need to add on back stack of fragment manager
	 * @param tag
	 *            name for the fragment
	 */
	public static void addFragment(AbstractFragmentActivity fragmentClassObj,
			FragmentActivity context, int fragmentId,
			boolean isAnimationRequired, boolean isBackstackRequired,
			String fragmentTag) {
		try {
			if (fragmentClassObj != null && context != null && fragmentId > 0) {
				// SignUpFragment farg = new SignUpFragment();
				FragmentTransaction fragmentTransaction = context
						.getSupportFragmentManager().beginTransaction();

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
					fragmentTransaction.addToBackStack(fragmentTag);

				}

				fragmentTransaction.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeHeaderFragmentWidgets(FragmentActivity context,
			int titleText) {

		View view;
		TextView title;

		view = (View) context.findViewById(R.id.header_fragment);
		title = (TextView) view.findViewById(R.id.header_text);
		if (titleText != 0) {
			title.setText(titleText);

		}

	}

	public static void onBackPressedCalled(final Context ctx) {
		FragmentManager fragManager = ((FragmentActivity) ctx)
				.getSupportFragmentManager();

		if (fragManager.getBackStackEntryCount() > 0) {

			fragManager.popBackStack();

		} else {

			new AlertDialogUtil().doubleOptionAlertDialog(ctx, null,
					ctx.getString(R.string.application_message),
					R.string.alert_ok, R.string.alert_cancel, 0,
					new OnAlertDialogFragmentListener() {

						@Override
						public void onPositiveButtonClick(int id) {
							if (id == 1) {
								((Activity) ctx).finish();
							}
						}

						@Override
						public void onNeutralizeButtonClick(int id) {

						}

						@Override
						public void onNegativeButtonClick(int id) {

						}

						@Override
						public void interfaceAttachError(int fragmentId,
								String errorResponse) { // TODO Auto-generated
														// method stub

						}
					}, 1);

		}
	}
	/*
	 * public static void goto_selectedFrag(final Context ctx) { FragmentManager
	 * fragManager = ((FragmentActivity) ctx) .getSupportFragmentManager();
	 * Fragment loginFrag = fragManager
	 * .findFragmentByTag(Constant.LOGIN_FRAGMENT); while
	 * (fragManager.getBackStackEntryCount() > 0) { if (loginFrag != null) {
	 * 
	 * loginFrag.onResume(); fragManager.popBackStack(); return; } else {
	 * 
	 * fragManager.popBackStack();
	 * 
	 * } } }
	 */

	/*
	 * public static void add_replace_fragment(Context ctx, String fragTag,
	 * AbstractFragmentActivity fragObj) { try { FragmentManager fragManager =
	 * ((FragmentActivity) ctx) .getSupportFragmentManager(); if
	 * (fragManager.getBackStackEntryCount() > 0) { fragManager.popBackStack();
	 * } else { replaceFragment(fragObj, ((FragmentActivity) ctx),
	 * R.id.mainfrag_frame, false, false, null); } else {
	 * 
	 * onBackPressedCalled(((FragmentActivity) ctx));
	 * 
	 * }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */
}