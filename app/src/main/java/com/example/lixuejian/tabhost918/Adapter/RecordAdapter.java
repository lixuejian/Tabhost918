package com.example.lixuejian.tabhost918.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lixuejian.tabhost918.R;
import com.example.lixuejian.tabhost918.Model.AllInfoHeartRecord;

import java.util.List;


public class RecordAdapter extends BaseAdapter {

	private Context mContext;
	private List<AllInfoHeartRecord> mRecordList;

	public RecordAdapter(Context context, List<AllInfoHeartRecord> list) {
		this.mContext = context;
		this.mRecordList = list;
	}

	@Override
	public int getCount() {
		return mRecordList.size();
	}

	@Override
	public Object getItem(int position) {
		return mRecordList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.xml_recordadapter_recorditem, null);
			holder.date = (TextView) convertView.findViewById(R.id.tv_recordadapter_date);
			holder.duration = (TextView) convertView.findViewById(R.id.tv_recordadapter_duration);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

        AllInfoHeartRecord item = mRecordList.get(position);
		holder.date.setText(item.getmDate());
		holder.duration.setText(item.getmMDuration());
		return convertView;
	}

	private class ViewHolder {
		TextView date;
		TextView duration;
	}
}
