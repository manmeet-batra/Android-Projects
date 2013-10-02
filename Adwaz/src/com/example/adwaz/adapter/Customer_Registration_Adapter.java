package com.example.adwaz.adapter;

import java.util.List;

import com.example.adwaz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class Customer_Registration_Adapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private ViewHolder holder;
	private List<String> mAddedServices;

	public Customer_Registration_Adapter(Context ctx, List<String> addedServices) {
		mContext = ctx;
		mAddedServices = addedServices;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAddedServices.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			holder = new ViewHolder();
			mInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg1 = mInflater.inflate(R.layout.customer_registeration_inflate,
					null);
			holder.mServices = (TextView) arg1
					.findViewById(R.id.customer_register_inflate_service);

			arg1.setTag(holder);

		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		holder.mServices.setText(mAddedServices.get(arg0));
		return arg1;
	}

	class ViewHolder {
		private TextView mServices;
	}

}
