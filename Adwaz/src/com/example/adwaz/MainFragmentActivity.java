package com.example.adwaz;

import android.content.Intent;
import android.os.Bundle;

import com.example.adwaz.abstractclasses.AbstractFragmentsMainActivity;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.fragments.LoginFragment;
import com.example.adwaz.fragments.RegisterFragment;
import com.example.adwaz.preferences.Sharedprefrences;
import com.example.adwaz.utils.FragmentsUtilClass;

public class MainFragmentActivity extends AbstractFragmentsMainActivity {
	Sharedprefrences sharedPrefs;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sharedPrefs = Sharedprefrences.getInstance(this);

		FragmentsUtilClass.replaceFragment(new LoginFragment(), this,
				R.id.main_frame, false, false, Constants.LOGIN_FRAGMENT_TAG,
				null);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager()
				.findFragmentByTag(Constants.REGISTER_FRAGMENT_TAG);

		if (registerFragment != null) {
			registerFragment.onActivityResult(arg0, arg1, arg2);
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		super.onBackPressed();
	}
}
