package com.example.adwaz.abstractclasses;

import com.example.adwaz.utils.FragmentsUtilClass;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class AbstractFragmentsMainActivity extends FragmentActivity {

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
	}

	@Override
	public void onBackPressed() {

		FragmentsUtilClass.onBackPressedCalled(this);
	}

}
