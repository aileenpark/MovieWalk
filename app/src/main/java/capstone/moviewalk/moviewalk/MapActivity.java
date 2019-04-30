package capstone.moviewalk.moviewalk;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    TMapView tmapview;

    LinearLayout ll_fragment;
    TextView tv_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ll_fragment = (LinearLayout)findViewById(R.id.ll_fragment);



        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", -1);
        double longitude = intent.getDoubleExtra("longitude", -1);
        final String address = intent.getStringExtra("address");
        final String name = intent.getStringExtra("name");
        final String information = intent.getStringExtra("information");





        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        tmapview = new TMapView(this);
        //현재위치 아이콘 표시
        tmapview.setIconVisibility(true);
        //gps함수호출
        setGps();
        //현재 보는 방향
        //tmapview.setCompassMode(true);

        tmapview.setHttpsMode(true);
        tmapview.setSKTMapApiKey("a9ccc80c-1188-4fde-a5e0-627c5a650bcc");
        tmapview.setCenterPoint(longitude, latitude);
        linearLayoutTmap.addView(tmapview);

        //화면중심을 단말기의 현재위치로 이동
        //tmapview.setTrackingMode(true);

        tmapview.setMapType(tmapview.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        //줌레벨//
        tmapview.setZoomLevel(12);




        final ArrayList alTMapPoint = new ArrayList();
        alTMapPoint.add(new TMapPoint(latitude, longitude));

        final Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.red_pin);

        for (int i = 0; i < alTMapPoint.size(); i++) {
            ////마커표시////
            TMapMarkerItem tItem = new TMapMarkerItem();  // 마커 아이콘 지정
            tItem.setIcon(bitmap);                       // 마커의 좌표 지정

            tItem.setTMapPoint((TMapPoint) alTMapPoint.get(i));       // 지도에 마커 추가
            tmapview.addMarkerItem("markerItem" + i, tItem);


            ////풍선위 정보제공
            tItem.setCalloutTitle(name);
            tItem.setCalloutSubTitle(address);
            tItem.setCanShowCallout(true);
            tItem.setAutoCalloutVisible(true);
            Bitmap bitmap_i = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.menu);
            tItem.setCalloutRightButtonImage(bitmap_i);


            tmapview.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
                @Override
                public boolean onPressUpEvent(ArrayList markerlist, ArrayList poilist, TMapPoint point, PointF pointf) {
                    return false;
                }

                @Override
                public boolean onPressEvent(ArrayList markerlist, ArrayList poilist, TMapPoint point, PointF pointf) {
                    //Toast.makeText(MapActivity.this, "정보를 제공중입니다", Toast.LENGTH_SHORT).show();
                    MapinfoFragment mapinfoFragment = (MapinfoFragment)getFragmentManager().findFragmentById(R.id.ll_fragment);
                    mapinfoFragment.changeFragmentTextView(information);
                    return false;
                }
            });
            replaceFragment();
        }

    }

    public void replaceFragment(){
        MapinfoFragment mapinfoFragment = new MapinfoFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll_fragment, mapinfoFragment);
        fragmentTransaction.commit();
    }






    ///////gps 표시
    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //tmapview.setLocationPoint(longitude, latitude);
                //tmapview.setCenterPoint(longitude, latitude);

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
        final LocationManager gps = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } //프래그먼트로 이용시 this 4개를 getActivity()로 수정할것
        gps.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                1000, //  최소 시간간격 (miliSecond)
                1, //  최소 변경거리 (m)
                mLocationListener);
    }
}