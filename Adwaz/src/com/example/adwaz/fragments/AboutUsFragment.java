package com.example.adwaz.fragments;

import android.view.LayoutInflater;
import android.view.View;

import com.example.adwaz.R;
import com.example.adwaz.abstractclasses.AbstractFragmentActivity;
import com.example.adwaz.utils.FragmentsUtilClass;

public class AboutUsFragment extends AbstractFragmentActivity {

	@Override
	public void getServerValues(String response, int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPositiveButtonClick(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNegativeButtonClick(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNeutralizeButtonClick(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void interfaceAttachError(int fragmentId, String errorResponse) {
		// TODO Auto-generated method stub

	}

	@Override
	public View initialization(LayoutInflater layoutInflator) {
		// TODO Auto-generated method stub
		return layoutInflator.inflate(R.layout.aboutus_frag, null);
	}

	@Override
	public void onResume() {
		super.onResume();
		FragmentsUtilClass.changeHeaderFragmentWidgets(getActivity(),
				R.string.aboutus_title);
	}
}
