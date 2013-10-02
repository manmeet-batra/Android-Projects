package com.example.adwaz.adapter;

import com.example.adwaz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context mContext;
	private ViewHolder holder;

	public HomeAdapter(Context ctx) {
		mContext = ctx;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
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
			arg1 = mInflater.inflate(R.layout.list_inflate, null);
			holder.mName = (TextView) arg1.findViewById(R.id.list_inflate_name);
			holder.mServices = (TextView) arg1
					.findViewById(R.id.list_inflate_services);

			arg1.setTag(holder);

		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		return arg1;
	}

	class ViewHolder {
		private TextView mName, mServices;
	}

}
