

package com.example.frank.wuhanjikong.ui.map.lib;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


public class LocationTask implements AMapLocationListener,
		OnLocationGetListener {

	private AMapLocationClient mLocationClient;

	private static LocationTask mLocationTask;

	private Context mContext;

	private OnLocationGetListener mOnLocationGetlisGetListener;

	private RegeocodeTask mRegecodeTask;

	private LocationTask(Context context) {
		mLocationClient = new AMapLocationClient(context);
		mLocationClient.setLocationListener(this);
		mRegecodeTask = new RegeocodeTask(context);
		mRegecodeTask.setOnLocationGetListener(this);
		mContext = context;
	}

	public void setOnLocationGetListener(
			OnLocationGetListener onGetLocationListener) {
		mOnLocationGetlisGetListener = onGetLocationListener;
	}

	public static LocationTask getInstance(Context context) {
		if (mLocationTask == null) {
			mLocationTask = new LocationTask(context);
		}
		return mLocationTask;
	}

	/**  
	 * 开启单次定位
	 */
	public void startSingleLocate() {
		AMapLocationClientOption option=new AMapLocationClientOption();
		option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		option.setOnceLocation(true);
		option.setGeoLanguage(AMapLocationClientOption.GeoLanguage.EN);
		mLocationClient.setLocationOption(option);
		mLocationClient.startLocation();

	}

	/**  
	 * 开启多次定位
	 */
	public void startLocate() {

		AMapLocationClientOption option=new AMapLocationClientOption();
		option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		option.setOnceLocation(false);
		option.setInterval(8*1000);
		option.setGeoLanguage(AMapLocationClientOption.GeoLanguage.EN);
		mLocationClient.setLocationOption(option);
		mLocationClient.startLocation();

	}

	/**  
	 * 结束定位，可以跟多次定位配合使用
	 */
	public void stopLocate() {
		mLocationClient.stopLocation();

	}

	/**  
	 * 销毁定位资源
	 */
	public void onDestroy() {
		mLocationClient.stopLocation();
		mLocationClient.onDestroy();
		mLocationTask = null;
	}



	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null && amapLocation.getErrorCode() == 0) {
			PositionEntity entity = new PositionEntity();
			entity.latitue = amapLocation.getLatitude();
			entity.longitude = amapLocation.getLongitude();

			if (!TextUtils.isEmpty(amapLocation.getAddress())) {
				entity.address = amapLocation.getAddress();
			}
			mOnLocationGetlisGetListener.onLocationGet(entity);

		}
	}

	@Override
	public void onLocationGet(PositionEntity entity) {

		// TODO Auto-generated method stub

	}

	@Override
	public void onRegecodeGet(PositionEntity entity) {

		// TODO Auto-generated method stub

	}

}
