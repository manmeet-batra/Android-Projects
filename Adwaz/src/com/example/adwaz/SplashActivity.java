package com.example.adwaz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.adwaz.abstractclasses.AbstractFragmentsMainActivity;

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

				startActivity(new Intent(SplashActivity.this,
						MainFragmentActivity.class));

				finish();
			}
		}.start();
	}

}
