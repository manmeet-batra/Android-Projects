package com.example.adwaz.fragments;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.adwaz.R;
import com.example.adwaz.SplashActivity;
import com.example.adwaz.abstractclasses.AbstractFragmentActivity;
import com.example.adwaz.adapter.HomeAdapter;
import com.example.adwaz.alerts.AlertDialogUtil;
import com.example.adwaz.async.WebServiceAsync;
import com.example.adwaz.async.WebServiceAsyncHttpPost;
import com.example.adwaz.async.WebServiceAsync.OnWebServiceProcess;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.listeners.OnAlertDialogFragmentListener;
import com.example.adwaz.preferences.Sharedprefrences;
import com.example.adwaz.utils.FragmentsUtilClass;

public class HomeFragment extends AbstractFragmentActivity implements
		OnWebServiceProcess {
	private ListView list;
	private HomeAdapter adapter;

	@Override
	public void getServerValues(String response, int id) {
		if (!TextUtils.isEmpty(response)) {
			Log.e("Home Fragment Customer List ", response);
			adapter = new HomeAdapter(getActivity());
			list.setAdapter(adapter);
		} else {
			new AlertDialogUtil().singleOptionAlertDialog(getActivity(), null,
					R.string.alert_ok,
					getResources().getString(R.string.no_records), 0,
					HomeFragment.this, Constants.GET_CUSTOMER_RESPONSEID);
		}

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
		View view = layoutInflator.inflate(R.layout.list_frag, null);
		list = (ListView) view.findViewById(R.id.list_frag_listview);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});

		// hit web service to get the list of customers for the logged in user
		Sharedprefrences mSharedprefrences = Sharedprefrences
				.getInstance(getActivity());
		WebServiceAsync.getInstance(getActivity(), this).get(
				Constants.GET_CUSTOMER_URL
						+ mSharedprefrences.getsharedstring(
								Constants.KEY_SERVICE_PROVIDER_REGISTERED, ""),
				Constants.GET_CUSTOMER_RESPONSEID, null, null);

		Log.e("Get CUtomer List URL ",
				Constants.GET_CUSTOMER_URL
						+ mSharedprefrences.getsharedstring(
								Constants.KEY_SERVICE_PROVIDER_REGISTERED, ""));

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FragmentsUtilClass.changeHeaderFragmentWidgets(getActivity(),
				R.string.home_title);
	}

}
