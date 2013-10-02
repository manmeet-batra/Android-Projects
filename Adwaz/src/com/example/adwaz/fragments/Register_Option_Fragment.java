package com.example.adwaz.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.FindListener;

import com.example.adwaz.R;
import com.example.adwaz.abstractclasses.AbstractFragmentActivity;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.utils.FragmentsUtilClass;

public class Register_Option_Fragment extends AbstractFragmentActivity {

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
		return layoutInflator.inflate(R.layout.register_dialog, null);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		
		getActivity().findViewById(R.id.register_dialog_customer)
				.setOnClickListener(this);
		getActivity().findViewById(R.id.register_dialog_service_provider)
				.setOnClickListener(this);
		getActivity().findViewById(R.id.register_dialog_close)
				.setOnClickListener(this);
		FragmentsUtilClass.changeHeaderFragmentWidgets(getActivity(),
				R.string.registration_title);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_dialog_customer:
			FragmentsUtilClass.replaceFragment(new Registration_Customer_Fragment(),
					getActivity(), R.id.main_frame, true, true,
					Constants.REGISTER_CUSTOMER_FRAGMENT_TAG, null);
			break;
		case R.id.register_dialog_service_provider:
			FragmentsUtilClass.replaceFragment(new RegisterFragment(),
					getActivity(), R.id.main_frame, true, true,
					Constants.REGISTER_FRAGMENT_TAG, null);
			break;

		case R.id.register_dialog_close:
			FragmentsUtilClass.onBackPressedCalled(getActivity());
		default:
			break;
		}
	}

}
