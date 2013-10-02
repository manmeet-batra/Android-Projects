package com.example.adwaz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;

import com.example.adwaz.abstractclasses.AbstractFragmentsMainActivity;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.fragments.HomeFragment;
import com.example.adwaz.preferences.Sharedprefrences;

public class MainTabFragmentActivity extends AbstractFragmentsMainActivity
		implements OnTabChangeListener {

	/** Declare variables */
	View homeTabView, searchTabView, registerTabView, profileTabView;

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub

	}

	/**
	 * Declare class variables
	 */
	// Fragment TabHost as mTabHost
	private static FragmentTabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bottom_tabs);

		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		homeTabView = LayoutInflater.from(this).inflate(R.layout.tab_home_item,
				null);
		searchTabView = LayoutInflater.from(this).inflate(
				R.layout.tab_search_item, null);
		registerTabView = LayoutInflater.from(this).inflate(
				R.layout.tab_custregistr_item, null);
		profileTabView = LayoutInflater.from(this).inflate(
				R.layout.tab_profile_item, null);

		// add tabs
		addTab();
	}

	/**
	 * Functionality to add tabs data for onCreate();
	 */
	private void addTab() {
		/* Home tab */
		tabHost.addTab(tabHost.newTabSpec(Constants.HOME_FRAGMENT_TAG)
				.setIndicator(homeTabView), HomeFragment.class, null);

		/* Search tab */

		tabHost.addTab(tabHost.newTabSpec(Constants.SEARCH_FRAGMENT_TAG)
				.setIndicator(searchTabView), HomeFragment.class, null);

		/* Register customer tab */
		tabHost.addTab(
				tabHost.newTabSpec(Constants.REGISTER_CUSTOMER_FRAGMENT_TAG)
						.setIndicator(registerTabView), HomeFragment.class,
				null);

		/* Profile tab */
		tabHost.addTab(tabHost.newTabSpec(Constants.PROFILE_FRAGMENT_TAG)
				.setIndicator(profileTabView), HomeFragment.class, null);

	}

	/**
	 * Functionality to set current tab on fore ground
	 */
	public static void setCurrentTab(int tabNumber) {
		try {
			tabHost.setCurrentTab(tabNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/** Option menu functionality to logout from the app */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.logout_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:

			// clear login credentials
			Sharedprefrences mSharedprefrences = Sharedprefrences
					.getInstance(MainTabFragmentActivity.this);
			mSharedprefrences.putsharedstring(Constants.EMAIL, "");
			mSharedprefrences.putsharedstring(Constants.PASSWORD, "");
			mSharedprefrences.putboolean(Constants.IS_CHECKED, false);

			// start login screen
			startActivity(new Intent(MainTabFragmentActivity.this,
					MainFragmentActivity.class));

			// close current activity
			finish();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
