package com.example.adwaz.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.example.adwaz.alerts.DoubleOptionAlertWithoutTitle;
import com.example.adwaz.async.WebServiceAsync;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.preferences.Sharedprefrences;
import com.example.adwaz.utils.FragmentsUtilClass;
import com.example.adwaz.utils.HorizontalListView;
import com.example.adwaz.utils.UplaodImageUtil;
import com.example.adwaz.utils.UtilityMethods;

public class Registration_Customer_Fragment extends AbstractFragmentActivity {
	private ImageView mImageView;
	private EditText mFirstName, mLastName, mEmail, mConfirmEmail, mAddService;
	private Button mSubmit, mAddServiceButton;
	private HorizontalListView mServiceHorizontalListView;
	private List<String> mserviceArrList;
	private Sharedprefrences mSharedprefrences;
	private Customer_Registration_Adapter mAdapter;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		mSharedprefrences = Sharedprefrences.getInstance(activity);
		super.onAttach(activity);
	}

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
		mImageView.setOnClickListener(this);
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

						String fName = mFirstName.getText().toString().trim();
						String lName = mLastName.getText().toString().trim();
						String email = mEmail.getText().toString().trim();
						StringBuilder services = new StringBuilder();
						if (mserviceArrList.size() > 0) {

							for (String n : mserviceArrList) {
								services.append(n).append(",");
								// can also do the following
								// nameBuilder.append("'").append(n.replaceAll("'",
								// "''")).append("',");
							}

							services.deleteCharAt(services.length() - 1);

							services.toString();
						}

						String[] key = { "fname", "lname", "email", "password",
								"services", "location", "owner" };
						String[] value = { mFirstName.getText().toString(),
								mLastName.getText().toString(),
								mEmail.getText().toString(), "abc",
								services.toString(), "Chd",
								Constants.KEY_SERVICE_PROVIDER_REGISTERED };
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
		case R.id.register_user_image:
			new DoubleOptionAlertWithoutTitle(getActivity(), getResources()
					.getString(R.string.image_option_alert), getResources()
					.getString(R.string.image_option_camera), getResources()
					.getString(R.string.image_option_gallery),
					DoubleOptionAlertWithoutTitle.IMAGE_PICK);
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
			mSharedprefrences.putboolean(Constants.KEY_CUSTOMER_REGISTERED,
					true);
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
		if (response != null) {
			try {
				JSONObject jsonObject = new JSONObject(response);
				if (jsonObject.has("register")) {
					String registrationResponse = jsonObject
							.getString("register");
					if (TextUtils.equals(registrationResponse, "yes")) {
						new AlertDialogUtil()
								.singleOptionAlertDialog(
										getActivity(),
										null,
										R.string.alert_ok,
										getString(R.string.register_customer_fragment_register_successfully),
										0, this, 1);

					} else if (TextUtils.equals(registrationResponse, "no")) {
						String errorResponse = jsonObject.getString("error");
						if (!TextUtils.equals(errorResponse, "")) {
							new AlertDialogUtil().singleOptionAlertDialog(
									getActivity(), null, R.string.alert_ok,
									errorResponse, 0, this, 2);
						}

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void setServerError(int id, String msg) {
		// TODO Auto-generated method stub
		super.setServerError(id, msg);
	}

	private boolean isEmptyOrNull(String value) {
		return !(!TextUtils.isEmpty(value) && !value.equals("null"));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Bitmap bitmap = null;
		if (resultCode == Activity.RESULT_OK) {

			Bitmap mBitmap;
			if (requestCode == DoubleOptionAlertWithoutTitle.SELECT_PICTURE_INTENT_KEY
					&& null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA,
						MediaStore.Images.Media.ORIENTATION };

				Cursor cursor = getActivity().getContentResolver().query(
						selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);

				int columnIndex1 = cursor.getColumnIndex(filePathColumn[1]);
				String orientation = cursor.getString(columnIndex1);

				cursor.close();

				bitmap = UplaodImageUtil.getBitmap(picturePath, 100, 100,
						getActivity());

			}

			else if (requestCode == DoubleOptionAlertWithoutTitle.CAMERA_ACTIVITY_INTENT_KEY) {
				// Bundle b = data.getExtras();
				// mBitmap = (Bitmap) b.get("data");

				File cameraFile = new File(
						Environment.getExternalStorageDirectory(), "Adwaz.jpg");

				Uri uri = Uri.parse(cameraFile.toString());
				bitmap = UplaodImageUtil.getBitmap(uri.toString(), 100, 100,
						getActivity());
			}

			mImageView.setImageBitmap(bitmap);

		}
	}

}