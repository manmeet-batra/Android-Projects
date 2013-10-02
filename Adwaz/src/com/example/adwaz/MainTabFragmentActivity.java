package com.example.adwaz;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.example.adwaz.abstractclasses.AbstractFragmentsMainActivity;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.fragments.HomeFragment;

public class MainTabFragmentActivity extends AbstractFragmentsMainActivity
		implements OnTabChangeListener {

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

		// add tabs
		addTab();
	}

	/**
	 * Functionality to add tabs data for onCreate();
	 */
	private void addTab() {
		/* Home tab */
		tabHost.addTab(
				tabHost.newTabSpec(Constants.HOME_FRAGMENT_TAG).setIndicator(
						null,
						getResources().getDrawable(R.drawable.ic_launcher)),
				HomeFragment.class, null);

		/* Search tab */
		tabHost.addTab(
				tabHost.newTabSpec(Constants.HOME_FRAGMENT_TAG).setIndicator(
						null,
						getResources().getDrawable(R.drawable.ic_launcher)),
				HomeFragment.class, null);

		/* Add Item tab */
		tabHost.addTab(
				tabHost.newTabSpec(Constants.HOME_FRAGMENT_TAG).setIndicator(
						null,
						getResources().getDrawable(R.drawable.ic_launcher)),
				HomeFragment.class, null);

		/* Notification tab */
		tabHost.addTab(
				tabHost.newTabSpec(Constants.HOME_FRAGMENT_TAG).setIndicator(
						null,
						getResources().getDrawable(R.drawable.ic_launcher)),
				HomeFragment.class, null);

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

}
