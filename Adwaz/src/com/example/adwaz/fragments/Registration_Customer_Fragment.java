package com.example.adwaz.fragments;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adwaz.R;
import com.example.adwaz.abstractclasses.AbstractFragmentActivity;
import com.example.adwaz.adapter.Customer_Registration_Adapter;
import com.example.adwaz.alerts.AlertDialogUtil;
import com.example.adwaz.async.WebServiceAsync;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.utils.FragmentsUtilClass;
import com.example.adwaz.utils.HorizontalListView;
import com.example.adwaz.utils.UtilityMethods;

public class Registration_Customer_Fragment extends AbstractFragmentActivity {
	private ImageView mImageView;
	private EditText mFirstName, mLastName, mEmail, mConfirmEmail, mAddService;
	private Button mSubmit, mAddServiceButton;
	private HorizontalListView mServiceHorizontalListView;
	private List<String> mserviceArrList;
	private Customer_Registration_Adapter mAdapter;

	@Override
	public View initialization(LayoutInflater layoutInflator) {
		// TODO Auto-generated method stub

		View view = layoutInflator.inflate(R.layout.frag_registraion_customer,
				null);
		mImageView = (ImageView) view.findViewById(R.id.register_user_image);
		mFirstName = (EditText) view.findViewById(R.id.register_first_name);
		mLastName = (EditText) view.findViewById(R.id.register_last_name);
		mEmail = (EditText) view.findViewById(R.id.register_email);
		mConfirmEmail = (EditText) view
				.findViewById(R.id.register_email_confirm);
		mAddService = (EditText) view
				.findViewById(R.id.register_customer_add_service_edit);
		view.findViewById(R.id.register_customer_submit).setOnClickListener(
				this);
		view.findViewById(R.id.register_customer_add_service_button)
				.setOnClickListener(this);
		mServiceHorizontalListView = (HorizontalListView) view
				.findViewById(R.id.item_detail_tag_horizontal);
		mserviceArrList = new ArrayList<String>();
		mserviceArrList.clear();
		mAdapter = new Customer_Registration_Adapter(getActivity(),
				mserviceArrList);
		mServiceHorizontalListView.setAdapter(mAdapter);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		FragmentsUtilClass.changeHeaderFragmentWidgets(getActivity(),
				R.string.registration_customer_title);
		super.onResume();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_customer_submit:
			if (!isEmptyOrNull(mFirstName.getText().toString())
					&& !isEmptyOrNull(mLastName.getText().toString())) {
				if (UtilityMethods.checkForValidEmail(mEmail.getText()
						.toString())
						&& UtilityMethods.checkForValidEmail(mConfirmEmail
								.getText().toString())) {
					if (TextUtils.equals(mEmail.getText().toString(),
							mConfirmEmail.getText().toString())) {
						String[] key = { "fname", "lname", "email", "password",
								"services", "location", "owner" };
						String[] value = { mFirstName.getText().toString(),
								mLastName.getText().toString(),
								mEmail.getText().toString(), "abc", "Service1",
								"Chd", "owner" };
						WebServiceAsync.getInstance(getActivity(), this)
								.getPair(
										Constants.BASE_URL
												+ "command=register_customer",
										0, key, value);
					} else {
						new AlertDialogUtil()
								.singleOptionAlertDialog(
										getActivity(),
										null,
										R.string.alert_ok,
										getString(R.string.register_email_notmatch_alert),
										0, null, 0);
					}

				} else {
					new AlertDialogUtil().singleOptionAlertDialog(
							getActivity(), null, R.string.alert_ok,
							getString(R.string.login_email_alert), 0, null, 0);
				}
			} else {
				new AlertDialogUtil().singleOptionAlertDialog(getActivity(),
						null, R.string.alert_ok,
						getString(R.string.register_blank_field_alert), 0,
						null, 0);
			}
			break;
		case R.id.register_customer_add_service_button:

			String added_service = mAddService.getText().toString().trim();
			if (!added_service.equalsIgnoreCase("")) {
				mserviceArrList.add(added_service);
				mAddService.setText("");
				this.mAdapter.notifyDataSetChanged();

			}

			break;
		default:
			break;
		}
	}

	@Override
	public void onPositiveButtonClick(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case 1:
			FragmentsUtilClass.clearBackStack(getActivity());
			break;

		default:
			break;
		}
	}

	@Override
	public void getServerValues(String response, int id) {
		// TODO Auto-generated method stub
		Log.i(getTag(), "response = " + response);
		new AlertDialogUtil()
				.singleOptionAlertDialog(
						getActivity(),
						null,
						R.string.alert_ok,
						getString(R.string.register_customer_fragment_register_successfully),
						0, this, 1);

	}

	@Override
	public void setServerError(int id, String msg) {
		// TODO Auto-generated method stub
		super.setServerError(id, msg);
	}

	private boolean isEmptyOrNull(String value) {
		return !(!TextUtils.isEmpty(value) && !value.equals("null"));
	}

}