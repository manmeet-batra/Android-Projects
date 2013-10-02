package com.example.adwaz.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.adwaz.R;
import com.example.adwaz.abstractclasses.AbstractFragmentActivity;
import com.example.adwaz.alerts.AlertDialogUtil;
import com.example.adwaz.async.WebServiceAsync;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.utils.FragmentsUtilClass;
import com.example.adwaz.utils.UtilityMethods;

public class ForgotFragment extends AbstractFragmentActivity {
	private EditText forgot_email;
	private Button forgot_submit;

	@Override
	public void getServerValues(String response, int id) {
		if (id == Constants.FORGOT_RESPONSEID) {
			if (response != null) {
				Log.e("Forgot Response", response);
				try {
					JSONObject jsonObject = new JSONObject(response);
					if (jsonObject.has("forgetpass")) {
						String mailSent = jsonObject.getString("forgetpass");
						if (TextUtils.equals(mailSent, "yes")) {

							new AlertDialogUtil()
									.singleOptionAlertDialog(
											getActivity(),
											null,
											R.string.alert_ok,
											getString(R.string.forgot_password_sent_alert),
											0, this, 1);

						} else {
							new AlertDialogUtil().singleOptionAlertDialog(
									getActivity(), null, R.string.alert_ok,
									jsonObject.getString("error"), 0, this, 1);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
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
		View view = layoutInflator.inflate(R.layout.forgot_password, null);
		forgot_email = (EditText) view.findViewById(R.id.forgot_edit_email);
		forgot_submit = (Button) view.findViewById(R.id.forgot_submit);
		forgot_submit.setOnClickListener(this);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FragmentsUtilClass.changeHeaderFragmentWidgets(getActivity(),
				R.string.forgot_title);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.forgot_submit:
			forgotMethod();

			break;

		default:
			break;
		}
	}

	private void forgotMethod() {
		String forgotEmail = forgot_email.getText().toString().trim();
		if (TextUtils.isEmpty(forgotEmail)) {
			new AlertDialogUtil().singleOptionAlertDialog(getActivity(), null,
					R.string.alert_ok, getString(R.string.forgot_email_alert),
					0, this, 1);

		} else {
			if (UtilityMethods.checkForValidEmail(forgotEmail)) {

				WebServiceAsync.getInstance(getActivity(), this).get(
						Constants.BASE_URL + "command=owner_forgotpass&email="
								+ forgotEmail, Constants.FORGOT_RESPONSEID,
						null, null);

				/*
				 * new AlertDialogUtil().singleOptionAlertDialog(getActivity(),
				 * null, R.string.alert_ok,
				 * getString(R.string.forgot_password_sent_alert), 0, this, 1);
				 */
			} else {
				new AlertDialogUtil().singleOptionAlertDialog(getActivity(),
						null, R.string.alert_ok,
						getString(R.string.forgot_email_alert), 0, this, 1);
			}

		}
	}
}