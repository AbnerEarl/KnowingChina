

package com.example.frank.wuhanjikong.ui.map.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.frank.wuhanjikong.R;

import java.util.Arrays;
import java.util.List;


public class RecomandAdapter extends BaseAdapter  {

	PositionEntity[] entities = new PositionEntity[] {
			new PositionEntity(39.908722, 116.397496, "天安门","010"),
			new PositionEntity(39.91141, 116.411306, "王府井","010"),
			new PositionEntity(39.908342, 116.375121, "西单","010"),
			new PositionEntity(39.990949, 116.481090, "方恒国际中心","010"),
			new PositionEntity(39.914529, 116.316648, "玉渊潭公园","010"),
			new PositionEntity(39.999093, 116.273945, "颐和园","010"),
			new PositionEntity(39.999022, 116.324698, "清华大学","010"),
			new PositionEntity(39.982940, 116.319802, "中关村","010"),
			new PositionEntity(39.933708, 116.454185, "三里屯","010"),
			new PositionEntity(39.941627, 116.435584, "东直门","010") };

	private List<PositionEntity> mPositionEntities;

	private Context mContext;

	public RecomandAdapter(Context context) {
		mContext = context;
		mPositionEntities = Arrays.asList(entities);
	
	}

	public void setPositionEntities(List<PositionEntity> entities) {
		this.mPositionEntities = entities;

	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return mPositionEntities.size();
	}

	@Override
	public Object getItem(int position) {

		return mPositionEntities.get(position);
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView textView = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			textView = (TextView) inflater.inflate(R.layout.view_recommond,
					null);
		} else {
			textView = (TextView) convertView;
		}
		textView.setText(mPositionEntities.get(position).address);
		return textView;
	}

	 

}
