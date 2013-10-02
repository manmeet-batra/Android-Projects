package com.example.adwaz.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.adwaz.R;

/**
 * This class is used as Widget instead of EditText.This class has also a custom
 * attribute which is used in xml file.
 * <P>
 * This attribute is customtypeface support string value pass name of typeface
 * of using in asses folder here. It will automatically set on EditText text.
 * </P>
 * 
 * @author amit.singh
 * 
 */
public class CustomEditText extends EditText {

	public CustomEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setTypeface(Typeface.DEFAULT);
		if (InputType.TYPE_TEXT_VARIATION_PASSWORD == getInputType()) {
			setTransformationMethod(new PasswordTransformationMethod());
		} else if (InputType.TYPE_CLASS_PHONE == getInputType()) {
			addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		}
		if (attrs != null) {

			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.CustomTextView, 0, 0);
			String typeface = a
					.getString(R.styleable.CustomTextView_customtypeface);
			if (!TextUtils.isEmpty(typeface))
				setTypeface(Typeface.createFromAsset(context.getAssets(),
						typeface));
			a.recycle();
		}
	}

	/**
	 * This method is used to clear editText value
	 */
	public void clear() {
		getText().clear();
	}

	/**
	 * This method is used to setTextSize. In this float value is changed to
	 * scaledDenisty and then set to the editText
	 * 
	 * @param px
	 *            float size of editText
	 */
	public void setEditTextSize(float px) {
		float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
		int sp = (int) (px / scaledDensity);
		setTextSize(sp);
	}

}
