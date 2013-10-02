package com.example.adwaz.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adwaz.R;
import com.example.adwaz.abstractclasses.AbstractFragmentActivity;
import com.example.adwaz.adapter.Customer_Registration_Adapter;
import com.example.adwaz.adapter.Profile_CustomerList_Adapter;
import com.example.adwaz.async.WebServiceAsync;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.preferences.Sharedprefrences;
import com.example.adwaz.utils.HorizontalListView;

public class ProfileFragment extends AbstractFragmentActivity {
	private TextView mName, mServices;
	private HorizontalListView mServiceHorizontalListView;
	private Customer_Registration_Adapter mservicesAdapter;
	private List<String> mserviceArrList;
	private ListView mcustomerList;
	private Profile_CustomerList_Adapter customerListAdapter;
	private List<String> customerList;
	private String mfName, mlName, memail, category, subcategory, address,
			phone;
	private Sharedprefrences mSharedprefrences;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		mSharedprefrences = Sharedprefrences.getInstance(activity);
		super.onAttach(activity);
	}

	@Override
	public void getServerValues(String response, int id) {
		if (response != null) {
			if (id == Constants.PROFILE_DATA_ID) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					mfName = jsonObject.getString("fname");
					mlName = jsonObject.getString("lname");
					memail = jsonObject.getString("email");
					category = jsonObject.getString("category");
					subcategory = jsonObject.getString("sub_category");
					address = jsonObject.getString("address");
					phone = jsonObject.getString("phone");
					mName.setText(mfName);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public View initialization(LayoutInflater layoutInflator) {
		// TODO Auto-generated method stub

		View view = layoutInflator.inflate(R.layout.frag_profile, null);
		mName = (TextView) view.findViewById(R.id.list_inflate_name);
		mServices = (TextView) view.findViewById(R.id.list_inflate_services);
		mServiceHorizontalListView = (HorizontalListView) view
				.findViewById(R.id.item_detail_tag_horizontal);
		mserviceArrList = new ArrayList<String>();
		mserviceArrList.clear();
		mserviceArrList.add("service1");
		mserviceArrList.add("service2");
		mservicesAdapter = new Customer_Registration_Adapter(getActivity(),
				mserviceArrList);
		mcustomerList = (ListView) view
				.findViewById(R.id.frag_profile_listView);
		customerListAdapter = new Profile_CustomerList_Adapter(getActivity(),
				customerList);

		mcustomerList.setAdapter(customerListAdapter);
		mServiceHorizontalListView.setAdapter(mservicesAdapter);
		return view;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		WebServiceAsync.getInstance(getActivity(), this).get(
				Constants.PROFILE_DETAIL_URL + "11", Constants.PROFILE_DATA_ID,
				null, null);
	}

}
