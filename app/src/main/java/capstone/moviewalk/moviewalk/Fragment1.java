package capstone.moviewalk.moviewalk;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

import capstone.moviewalk.moviewalk.R;


public class Fragment1 extends android.app.Fragment {


    public static Fragment1 newInstance(){
        return new Fragment1();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }



    //////////////////맵 나타내기
    TMapView tmapview;
    ViewGroup mapViewContainer;
    public static boolean isStart = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, container, false);
        mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view);
        //final
        tmapview = new TMapView(getActivity());
        tmapview.setHttpsMode(true);

        tmapview.setSKTMapApiKey("a9ccc80c-1188-4fde-a5e0-627c5a650bcc");
        mapViewContainer.addView(tmapview);
        isStart = true;


        //현재위치 표시//
        tmapview.setIconVisibility(true);
        setGps();
        ///////////////


        ////마커 나타내기////
        final ArrayList alTMapPoint = new ArrayList();
        alTMapPoint.add(new TMapPoint(37.505426, 126.957169));
        final Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.red_pin);


        for(int i=0; i<alTMapPoint.size(); i++) {
            TMapMarkerItem tItem = new TMapMarkerItem();  // 마커 아이콘 지정
            tItem.setIcon(bitmap);                       // 마커의 좌표 지정
            tItem.setTMapPoint((TMapPoint) alTMapPoint.get(i));       // 지도에 마커 추가
            tmapview.addMarkerItem("markerItem" + i, tItem);
        }



        ////마커클릭 이벤트////
        tmapview.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {

            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {

                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {

                ////마커클릭시 다음프래그먼트로 이동
                TMapMarkerItem marker = null;
                for(int i=0; i<arrayList.size(); i++) {
                    marker = arrayList.get(i);
                    if (!arrayList.get(0).equals(null)) {
                        String markerID = marker.getID();

                        Bundle bundle = new Bundle(i);
                        bundle.putString("markerID", markerID);

                        android.app.FragmentManager fragmentManager = getFragmentManager();
                        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        Fragment1_info secondFragment = new Fragment1_info();
                        secondFragment.setArguments(bundle);

                        fragmentTransaction.replace(R.id.main_frame,secondFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                }
                return false;
            }
        });
        return v;
    }



    ///////gps 표시
    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tmapview.setLocationPoint(longitude, latitude);
                tmapview.setCenterPoint(longitude, latitude);

            }

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };


    /////gps표시
    public void setGps() {
        final LocationManager gps = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        gps.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                1000, //  최소 시간간격 (miliSecond)
                1, //  최소 변경거리 (m)
                mLocationListener);
    }








    @Override
    public void onPause() {
        super.onPause();

        mapViewContainer.removeAllViews();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isStart = false;
        mapViewContainer.removeAllViews();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isStart = false;
    }
}