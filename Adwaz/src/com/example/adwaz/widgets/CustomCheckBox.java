package com.example.adwaz.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

/**
 * This is CustomCheckBox class used to show image. This class also used to
 * change the state of the image which looks similar to checkbox.
 * 
 * @author amit.singh
 * 
 */
public class CustomCheckBox extends ImageView implements Checkable {

	public CustomCheckBox(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

	}

	private static final int[] CheckedStateSet = { android.R.attr.state_checked };

	private boolean mChecked = false;

	@Override
	public boolean isChecked() {
		return mChecked;
	}

	@Override
	public void setChecked(boolean b) {
		if (b != mChecked) {
			mChecked = b;
		}
		refreshDrawableState();
	}

	@Override
	public void toggle() {
		setChecked(!isChecked());
	}

	@Override
	public int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CheckedStateSet);
		}
		return drawableState;
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		invalidate();
	}

}
