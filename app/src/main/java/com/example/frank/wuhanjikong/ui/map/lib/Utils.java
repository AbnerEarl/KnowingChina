
  
package com.example.frank.wuhanjikong.ui.map.lib;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.log.L;

import java.util.ArrayList;
import java.util.List;


public class Utils {

	private static ArrayList<Marker>markers=new ArrayList<Marker>();
	
	/**  
	 * 添加模拟测试的车的点
	 */
	public static void addEmulateData(AMap amap, LatLng center, List<Double> latituList,List<Double> longtituList,List<Integer> shopStyle){
		 if(markers.size()==0){

			 L.g("latituList.size()",latituList.size()+"");
				for(int i=0;i<latituList.size();i++){
				double latitudeDelt=latituList.get(i);
				double longtitudeDelt=longtituList.get(i);
				L.g("添加了标记：",i+"");
				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.setFlat(true);
				markerOptions.anchor(0.5f, 0.5f);
				if (shopStyle.get(i)==1){
					BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory.fromResource(R.drawable.m18);
					markerOptions.icon(bitmapDescriptor);
				}else if (shopStyle.get(i)==2){
					BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory.fromResource(R.drawable.m21);
					markerOptions.icon(bitmapDescriptor);
				}else if (shopStyle.get(i)==3){
					BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory.fromResource(R.drawable.m20);
					markerOptions.icon(bitmapDescriptor);
				}else if (shopStyle.get(i)==4){
					BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory.fromResource(R.drawable.m16);
					markerOptions.icon(bitmapDescriptor);
				}



					//markerOptions.position(new LatLng(center.latitude+latitudeDelt, center.longitude+longtitudeDelt));
				markerOptions.position(new LatLng(latitudeDelt, longtitudeDelt));
				Marker marker=amap.addMarker(markerOptions);
				markers.add(marker);
				}
		 }
		 else{
			 for(Marker marker:markers){
				//double latitudeDelt=(Math.random()-0.5)*0.1;
				//double longtitudeDelt=(Math.random()-0.5)*0.1;
				//marker.setPosition(new LatLng(center.latitude+latitudeDelt, center.longitude+longtitudeDelt));
				marker.setPosition(marker.getOptions().getPosition());

			 }
		 }
	}


	/*public static void addEmulateData(AMap amap, LatLng center){
		if(markers.size()==0){
			BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory
					.fromResource(R.drawable.icon_car);

			for(int i=0;i<20;i++){
				double latitudeDelt=(Math.random()-0.5)*0.1;
				double longtitudeDelt=(Math.random()-0.5)*0.1;
				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.setFlat(true);
				markerOptions.anchor(0.5f, 0.5f);
				markerOptions.icon(bitmapDescriptor);
				markerOptions.position(new LatLng(center.latitude+latitudeDelt, center.longitude+longtitudeDelt));
				Marker marker=amap.addMarker(markerOptions);
				markers.add(marker);
			}
		}
		else{
			for(Marker marker:markers){
				double latitudeDelt=(Math.random()-0.5)*0.1;
				double longtitudeDelt=(Math.random()-0.5)*0.1;
				marker.setPosition(new LatLng(center.latitude+latitudeDelt, center.longitude+longtitudeDelt));

			}
		}
	}*/

	/**
	 * 移除marker
	 */
	public static void removeMarkers() {
		for(Marker marker:markers){
			marker.remove();
			marker.destroy();
		}
		markers.clear();
	}
	
}
  
