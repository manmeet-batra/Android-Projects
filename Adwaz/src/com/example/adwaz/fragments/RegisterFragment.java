package com.example.adwaz.fragments;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.adwaz.R;
import com.example.adwaz.abstractclasses.AbstractFragmentActivity;
import com.example.adwaz.alerts.AlertDialogUtil;
import com.example.adwaz.alerts.DoubleOptionAlertWithoutTitle;
import com.example.adwaz.async.WebServiceAsync;
import com.example.adwaz.async.WebServiceAsync.OnWebServiceProcess;
import com.example.adwaz.async.WebServiceAsyncHttpPost;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.preferences.Sharedprefrences;
import com.example.adwaz.utils.FragmentsUtilClass;
import com.example.adwaz.utils.UplaodImageUtil;
import com.example.adwaz.utils.UtilityMethods;
import com.example.adwaz.widgets.CustomEditText;

public class RegisterFragment extends AbstractFragmentActivity implements
		OnWebServiceProcess {
	// private ImageLoaderAsync mImageLoaderAsync;
	private ImageView userImage;
	private EditText firstName, lastName, email, confirmEmail, phone;
	private CustomEditText password, confirmPassword;
	private Spinner category, subcategory;
	private Button submit;
	private String mSubCategory, mCategory;
	private String[] mCateogries;
	private Sharedprefrences mpreferences;
	private Bitmap mbitmap = null;

	@Override
	public void getServerValues(String response, int id) {
		if (response != null) {
			Log.e("Registration Response ", response);
			switch (id) {
			case Constants.REGISTRATION_RESPONSEID:
				try {
					JSONObject jsonObj = new JSONObject(response);
					String registeredUsedId = jsonObj.getString("id");
					if (!TextUtils.equals(registeredUsedId, "")) {
						mpreferences.putsharedstring(
								Constants.KEY_SERVICE_PROVIDER_REGISTERED,
								registeredUsedId);
						Log.e("Registration id ",
								mpreferences
										.getsharedstring(
												Constants.KEY_SERVICE_PROVIDER_REGISTERED,
												null));
					}
					String registration_successfull = jsonObj
							.getString("register");
					if (TextUtils.equals(registration_successfull, "yes")) {
						if (mbitmap != null) {
							byte[] imageBytesArray = UplaodImageUtil
									.getBitmapByteArray(mbitmap);
							/*
							 * WebServiceAsync .getInstance(getActivity(), this)
							 * .post(Constants.
							 * OWNER_REGISTERED_PROFILE_IMAGE_UPLOAD_URL,
							 * Constants.REGISTERED_PROFILE_IMAGE_UPLOAD_ID,
							 * null, null);
							 */
							new WebServiceAsyncHttpPost(
									getActivity(),
									Constants.OWNER_REGISTERED_PROFILE_IMAGE_UPLOAD_URL
											+ mpreferences
													.getsharedstring(
															Constants.KEY_SERVICE_PROVIDER_REGISTERED,
															null),
									Constants.REGISTERED_PROFILE_IMAGE_UPLOAD_ID,
									this, null, null, imageBytesArray)
									.execute();

						}
						else{
							
							  new AlertDialogUtil() .singleOptionAlertDialog(
							  getActivity(), null, R.string.alert_ok,
							  getString(R.string
							  .register_successfull_registration_alert), 0, this,
							  2);
							 
						}
						

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;

			case Constants.REGISTRATION_CATEGORIES_RESPONSEID:
				Log.v("Registration Categories Response ", response);

				try {
					JSONArray categoriesJsonArray = new JSONArray(response);
					mCateogries = new String[categoriesJsonArray.length()];
					for (int count = 0; count < categoriesJsonArray.length(); count++) {
						JSONObject categoryType = new JSONObject(
								categoriesJsonArray.getString(count));

						mCateogries[count] = categoryType.getString("type");
					}

					Handler handle = new Handler();
					handle.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									getActivity(),
									android.R.layout.simple_list_item_single_choice,
									mCateogries);
							adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							category.setAdapter(adapter);
							subcategory.setAdapter(adapter);

						}
					});

				} catch (JSONException e) {
				}

				break;

			case Constants.REGISTRATION_SUB_CATEGORIES_RESPONSEID:
				Log.v("Registration Sub Categories Response ", response);

				break;

			case Constants.REGISTERED_PROFILE_IMAGE_UPLOAD_ID:
				Log.v("Upload Image Response", response);
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void onPositiveButtonClick(int id) {
		if (id == 2) {
		/*	FragmentsUtilClass.onBackPressedCalled(getActivity());*/
			FragmentsUtilClass.clearBackStack(getActivity());
		}

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
		View view = layoutInflator.inflate(R.layout.registration_frag, null);
		userImage = (ImageView) view.findViewById(R.id.register_user_image);
		firstName = (EditText) view.findViewById(R.id.register_first_name);
		lastName = (EditText) view.findViewById(R.id.register_last_name);
		email = (EditText) view.findViewById(R.id.register_email);
		confirmEmail = (EditText) view
				.findViewById(R.id.register_email_confirm);
		password = (CustomEditText) view.findViewById(R.id.register_password);
		confirmPassword = (CustomEditText) view
				.findViewById(R.id.register_password_confirm);
		category = (Spinner) view.findViewById(R.id.register_category);
		subcategory = (Spinner) view.findViewById(R.id.register_sub_category);

		submit = (Button) view.findViewById(R.id.register_submit);
		/*
		 * category.setAdapter(adapter); subcategory.setAdapter(adapter);
		 */submit.setOnClickListener(this);
		userImage.setOnClickListener(this);
		mpreferences = Sharedprefrences.getInstance(getActivity());

		/*
		 * WebServiceAsync.getInstance(getActivity(), this).get(
		 * Constants.REGISTRATION_SUB_CATEGORIES_URL,
		 * Constants.REGISTRATION_SUB_CATEGORIES_RESPONSEID, null, null);
		 */
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FragmentsUtilClass.changeHeaderFragmentWidgets(getActivity(),
				R.string.registration_title);
		WebServiceAsync.getInstance(getActivity(), this).get(
				Constants.REGISTRATION_CATEGORIES_URL,
				Constants.REGISTRATION_CATEGORIES_RESPONSEID, null, null);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.register_submit:
			sunbmitMethod();
			break;

		case R.id.register_user_image:
			new DoubleOptionAlertWithoutTitle(getActivity(), getResources()
					.getString(R.string.image_option_alert), getResources()
					.getString(R.string.image_option_camera), getResources()
					.getString(R.string.image_option_gallery),
					DoubleOptionAlertWithoutTitle.IMAGE_PICK);

		default:
			break;
		}

	}

	void sunbmitMethod() {

		String first_name = firstName.getText().toString().trim();
		String last_name = lastName.getText().toString().trim();
		String user_email = email.getText().toString().trim();
		String confirm_email = confirmEmail.getText().toString().trim();
		String user_password = password.getText().toString().trim();
		String user_confirm_password = confirmPassword.getText().toString()
				.trim();
		mSubCategory = subcategory.getSelectedItem().toString();
		mCategory = category.getSelectedItem().toString();
		if (TextUtils.isEmpty(first_name) || TextUtils.isEmpty(last_name)
				|| TextUtils.isEmpty(user_email)
				|| TextUtils.isEmpty(confirm_email)
				|| TextUtils.isEmpty(user_password)
				|| TextUtils.isEmpty(user_confirm_password)) {
			new AlertDialogUtil().singleOptionAlertDialog(getActivity(), null,
					R.string.alert_ok,
					getString(R.string.register_blank_field_alert), 0, this, 1);
		} else {
			if (UtilityMethods.checkForValidEmail(user_email)
					&& UtilityMethods.checkForValidEmail(confirm_email)) {

				if (TextUtils.equals(user_email, confirm_email)) {

					if (TextUtils.equals(user_password, user_confirm_password)) {
						/*
						 * new AlertDialogUtil() .singleOptionAlertDialog(
						 * getActivity(), null, R.string.alert_ok,
						 * getString(R.string
						 * .register_successfull_registration_alert), 0, this,
						 * 2);
						 */

						Log.e("Registeration final URl", ""
								+ Constants.BASE_URL
								+ "command=register_owner&fname=" + first_name
								+ "&lname=" + last_name + "&email="
								+ user_email + "&password=" + user_password
								+ "&category=" + mCategory + "&subCategory="
								+ mSubCategory);

						WebServiceAsync.getInstance(getActivity(), this).get(
								Constants.BASE_URL
										+ "command=register_owner&fname="
										+ first_name + "&lname=" + last_name
										+ "&email=" + user_email + "&password="
										+ user_password + "&category="
										+ mCategory + "&subCategory="
										+ mSubCategory,
								Constants.REGISTRATION_RESPONSEID, null, null);

					} else {
						new AlertDialogUtil()
								.singleOptionAlertDialog(
										getActivity(),
										null,
										R.string.alert_ok,
										getString(R.string.register_password_notmatch_alert),
										0, this, 4);
					}
				} else {
					new AlertDialogUtil().singleOptionAlertDialog(
							getActivity(), null, R.string.alert_ok,
							getString(R.string.register_email_notmatch_alert),
							0, this, 3);
				}
			} else {
				new AlertDialogUtil().singleOptionAlertDialog(getActivity(),
						null, R.string.alert_ok,
						getString(R.string.login_email_alert), 0, this, 3);
			}

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		/* Bitmap bitmap = null; */
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

				mbitmap = UplaodImageUtil.getBitmap(picturePath, 100, 100,
						getActivity());

			}

			else if (requestCode == DoubleOptionAlertWithoutTitle.CAMERA_ACTIVITY_INTENT_KEY) {
				// Bundle b = data.getExtras();
				// mBitmap = (Bitmap) b.get("data");

				File cameraFile = new File(
						Environment.getExternalStorageDirectory(), "Adwaz.jpg");

				Uri uri = Uri.parse(cameraFile.toString());
				mbitmap = UplaodImageUtil.getBitmap(uri.toString(), 100, 100,
						getActivity());
			}

			userImage.setImageBitmap(mbitmap);

		}
	}
}
