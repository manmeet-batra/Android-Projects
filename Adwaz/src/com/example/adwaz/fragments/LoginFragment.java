package com.example.adwaz.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adwaz.R;
import com.example.adwaz.abstractclasses.AbstractFragmentActivity;
import com.example.adwaz.alerts.AlertDialogUtil;
import com.example.adwaz.async.WebServiceAsync;
import com.example.adwaz.async.WebServiceAsync.OnWebServiceProcess;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.preferences.Sharedprefrences;
import com.example.adwaz.utils.FragmentsUtilClass;
import com.example.adwaz.utils.UtilityMethods;
import com.example.adwaz.widgets.CustomCheckBox;
import com.example.adwaz.widgets.CustomEditText;

public class LoginFragment extends AbstractFragmentActivity implements
		OnWebServiceProcess {

	private EditText email;
	private CustomEditText password;
	private TextView forgotPswd;
	private Button login, signUp;
	private CustomCheckBox mCheckBox;
	private Sharedprefrences mSharedprefrences;

	@Override
	public void getServerValues(String response, int id) {
		if (response != null) {
			try {
				JSONObject jsonObject = new JSONObject(response);
				String loginresponse = jsonObject.getString("login");
				if (TextUtils.equals(loginresponse, "yes")) {

					new AlertDialogUtil().singleOptionAlertDialog(
							getActivity(), null, R.string.alert_ok,
							getString(R.string.login_successful_alert), 0,
							this, 4);

				} else {

					String error = jsonObject.getString("error");
					new AlertDialogUtil().singleOptionAlertDialog(
							getActivity(), null, R.string.alert_ok, error, 0,
							this, 5);
				}

			} catch (JSONException e) {
				e.printStackTrace();

			}
		}

	}

	@Override
	public void onPositiveButtonClick(int id) {
		switch (id) {
		case 1:

			break;
		case 2:

			break;

		case 3:
			break;
		case 4:
			homefragment();
			break;
		case 5:
			break;
		default:
			break;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		mSharedprefrences = Sharedprefrences.getInstance(activity);
		super.onAttach(activity);
	}

	@Override
	public View initialization(LayoutInflater layoutInflator) {
		// TODO Auto-generated method stub
		View view = layoutInflator.inflate(R.layout.login_frag, null);
		email = (EditText) view.findViewById(R.id.edit_email);
		password = (CustomEditText) view.findViewById(R.id.edit_password);
		login = (Button) view.findViewById(R.id.login_login);
		forgotPswd = (TextView) view.findViewById(R.id.forgot_password);
		signUp = (Button) view.findViewById(R.id.login_signup);
		mCheckBox = (CustomCheckBox) view.findViewById(R.id.remember_me_check);
		mCheckBox.setOnClickListener(this);
		login.setOnClickListener(this);
		forgotPswd.setOnClickListener(this);
		signUp.setOnClickListener(this);
		if (mSharedprefrences.getboolean(Constants.IS_CHECKED, false)) {
			email.setText(mSharedprefrences
					.getsharedstring(Constants.EMAIL, ""));
			password.setText(mSharedprefrences.getsharedstring(
					Constants.PASSWORD, ""));
			mCheckBox.setChecked(true);
		}
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FragmentsUtilClass.changeHeaderFragmentWidgets(getActivity(),
				R.string.login_title);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.login_login:
			loginFunc();
			break;
		case R.id.login_signup:
			signupFunc();
			break;
		case R.id.forgot_password:
			forgotPassword();
			break;
		case R.id.remember_me_check:
			mCheckBox.toggle();
			break;
		/*
		 * case R.id.login_about_us: aboutUsFunc();
		 */
		// break;
		default:
			break;
		}

	}

	private void loginFunc() {
		String emailString = email.getText().toString().trim();
		String passwoString = password.getText().toString().trim();

		if (TextUtils.isEmpty(emailString)) {
			new AlertDialogUtil().singleOptionAlertDialog(getActivity(), null,
					R.string.alert_ok, getString(R.string.login_email_alert),
					0, this, 1);
		} else if (TextUtils.isEmpty(passwoString)) {

			new AlertDialogUtil().singleOptionAlertDialog(getActivity(), null,
					R.string.alert_ok, getString(R.string.login_pwsd_alert), 0,
					this, 2);
			// alert for password
		} else {

			if (UtilityMethods.checkForValidEmail(emailString)) {
				/*
				 * new AlertDialogUtil().singleOptionAlertDialog(getActivity(),
				 * null, R.string.alert_ok,
				 * getString(R.string.login_successful_alert), 0, this, 3);
				 */
				if (mCheckBox.isChecked()) {
					mSharedprefrences.putsharedstring(Constants.EMAIL,
							emailString);
					mSharedprefrences.putsharedstring(Constants.PASSWORD,
							passwoString);
					mSharedprefrences.putboolean(Constants.IS_CHECKED, true);
				} else {
					mSharedprefrences.putsharedstring(Constants.EMAIL, "");
					mSharedprefrences.putsharedstring(Constants.PASSWORD, "");
					mSharedprefrences.putboolean(Constants.IS_CHECKED, false);
				}
				WebServiceAsync.getInstance(getActivity(), this).get(
						Constants.BASE_URL + "command=login_owner&username="
								+ emailString + "&password=" + passwoString,
						Constants.LOGIN_RESPONSEID, null, null);
				// homefragment();
			} else {
				// Login success msg
				new AlertDialogUtil().singleOptionAlertDialog(getActivity(),
						null, R.string.alert_ok,
						getString(R.string.login_email_alert), 0, this, 3);
			}
		}

	}

	private void signupFunc() {

		FragmentsUtilClass.replaceFragment(new Register_Option_Fragment(),
				getActivity(), R.id.main_frame, true, true,
				Constants.REGISTER_OPTION_FRAGMENT_TAG, null);
	}

	private void aboutUsFunc() {
		FragmentsUtilClass.replaceFragment(new AboutUsFragment(),
				getActivity(), R.id.main_frame, true, true,
				Constants.ABOUTUS_FRAGMENT_TAG, null);
	}

	private void forgotPassword() {
		FragmentsUtilClass.replaceFragment(new ForgotFragment(), getActivity(),
				R.id.main_frame, true, true, Constants.ABOUTUS_FRAGMENT_TAG,
				null);
	}

	private void homefragment() {
		FragmentsUtilClass.replaceFragment(new HomeFragment(), getActivity(),
				R.id.main_frame, false, false, Constants.HOME_FRAGMENT_TAG,
				null);
	}
}
