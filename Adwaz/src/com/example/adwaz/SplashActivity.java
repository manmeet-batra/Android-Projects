package com.example.adwaz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.adwaz.abstractclasses.AbstractFragmentsMainActivity;
import com.example.adwaz.constant.Constants;
import com.example.adwaz.preferences.Sharedprefrences;

public class SplashActivity extends AbstractFragmentsMainActivity {

	private final int DELAY_INTERVAL = 3 * 1000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		CountDownTimer();
	}

	/**
	 * Functionality to delay time and start with login view
	 */

	void CountDownTimer() {
		new CountDownTimer(DELAY_INTERVAL, DELAY_INTERVAL) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {

				Sharedprefrences mSharedprefrences = Sharedprefrences
						.getInstance(SplashActivity.this);
				if (mSharedprefrences.getboolean(Constants.IS_CHECKED, false)) {

					// start home tab screen because user have choodes to
					// remember login
					startActivity(new Intent(SplashActivity.this,
							MainTabFragmentActivity.class)
							.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
									| Intent.FLAG_ACTIVITY_NEW_TASK));
				} else {

					startActivity(new Intent(SplashActivity.this,
							MainFragmentActivity.class));
				}
				finish();
			}
		}.start();
	}

}
