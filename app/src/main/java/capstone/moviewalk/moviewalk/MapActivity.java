package capstone.moviewalk.moviewalk;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
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

        ll_fragment = (LinearLayout) findViewById(R.id.ll_fragment);


        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", -1);
        double longitude = intent.getDoubleExtra("longitude", -1);
        final String address = intent.getStringExtra("address");
        final String name = intent.getStringExtra("name");
        final String information = intent.getStringExtra("information");
        final String infoURL_map = intent.getStringExtra("infoURL_map");


        //final String name_route = intent.getStringExtra("name_route"); //route때 이용
        //double myStrings = intent.getDoubleExtra("strings", -1);
        double latitude_route = intent.getDoubleExtra("latitude_route", -1);
        double longitude_route = intent.getDoubleExtra("longitude_route", -1);


        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        tmapview = new TMapView(this);
        //현재위치 아이콘 표시
        tmapview.setIconVisibility(true);
        //gps함수호출
        //setGps();
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

        final Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.poi_dot);

        for (int i = 0; i < alTMapPoint.size(); i++) { //다중마커생성, 사실상 필요없음..
            ////마커표시////
            final TMapMarkerItem tItem = new TMapMarkerItem();  // 마커 아이콘 지정
            tItem.setIcon(bitmap);                       // 마커의 좌표 지정

            tItem.setTMapPoint((TMapPoint) alTMapPoint.get(i));       // 지도에 마커 추가
            tmapview.addMarkerItem("markerItem" + i, tItem);


            ////풍선위 정보제공
            tmapview.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
                @Override
                public boolean onPressUpEvent(ArrayList markerlist, ArrayList poilist, TMapPoint point, PointF pointf) {
                    return false;
                }

                @Override
                public boolean onPressEvent(ArrayList markerlist, ArrayList poilist, TMapPoint point, PointF pointf) {
                    //tItem.setCalloutTitle(name);
                    //tItem.setCalloutSubTitle(address);
                    tItem.setCalloutTitle(address);
                    tItem.setCanShowCallout(true);
                    tItem.setAutoCalloutVisible(true);

                    Bitmap bitmap_i = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.menu);
                    tItem.setCalloutRightButtonImage(bitmap_i);
                    return false;
                }
            });



            // RightButton 눌러야 fragment로 넘어감.
            tmapview.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
                @Override
                public void onCalloutRightButton(TMapMarkerItem markerItem) {
                    MapinfoFragment mapinfoFragment = (MapinfoFragment) getFragmentManager().findFragmentById(R.id.ll_fragment);
                    mapinfoFragment.changeFragmentTextView(information);
                    mapinfoFragment.changeFragmentURL(infoURL_map); // website 버튼에 infoURL_map 넘기기
                    //return false;
                }
            });



            // 맵 아무데나 누르면 fragment로 넘어감
            /*tmapview.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
                @Override
                public boolean onPressUpEvent(ArrayList markerlist, ArrayList poilist, TMapPoint point, PointF pointf) {
                    return false;
                }

                @Override
                public boolean onPressEvent(ArrayList markerlist, ArrayList poilist, TMapPoint point, PointF pointf) {
                    //Toast.makeText(MapActivity.this, "정보를 제공중입니다", Toast.LENGTH_SHORT).show();

                    //마커 클릭시에만 프래그먼트 뜨게하기 (실패)
                    //ArrayList marker = (ArrayList) ArrayList.get(i);
                    //if(!markerlist.get(0).equals(null)) {
                    //    String markerID = marker.get(0);
                    //}

                    MapinfoFragment mapinfoFragment = (MapinfoFragment) getFragmentManager().findFragmentById(R.id.ll_fragment);
                    mapinfoFragment.changeFragmentTextView(information);
                    mapinfoFragment.changeFragmentURL(infoURL_map); // website 버튼에 infoURL_map 넘기기
                    return false;
                }
            });*/




            replaceFragment();
        }







        /*
        /////////
        //경로표시
        /////////

        TMapPoint point1 = new TMapPoint(37.570841, 126.985302); // 종각역(출발지)
        TMapPoint point2 = new TMapPoint(37.5536417, 126.9705125); // 서울역(도착지)
        //TMapPoint point3 = new TMapPoint(37.5647968,126.9739206); // 시청역(경유지)
        //TMapPoint point4 = new TMapPoint(37.575838,126.971386); // 경복궁역(경유지)

        TMapPoint route[] = new TMapPoint[5];
        for (int i = 0; i < route.length; i++) {
            route[i] = new TMapPoint(latitude_route, longitude_route);
        }

        ArrayList<TMapPoint> passList = new ArrayList<>();
        for (int i = 0; i < passList.size(); i++) {
            passList.add(route[i]);
        }
        //TMapPoint route = new TMapPoint(latitude_route, longitude_route);


        //passList.add(point3);
        //passList.add(point4); // 배열순대로 출력됨.


        tmapview.removeTMapPath();
        tmapview.setTrackingMode(true);

        TMapData tmapdata = new TMapData();
        tmapdata.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, point1, point2, passList, 10, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(10);
                tmapview.addTMapPath(tMapPolyLine);
            }
        });

        Bitmap start = BitmapFactory.decodeResource(getResources(), R.drawable.poi_dot);
        Bitmap end = BitmapFactory.decodeResource(getResources(), R.drawable.end);

        tmapview.setTMapPathIcon(start, end);
        tmapview.zoomToTMapPoint(point1, point2);

        //////////////
        ////경료표시 끝
        //////////////
*/





    }

    public void replaceFragment() {
        MapinfoFragment mapinfoFragment = new MapinfoFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll_fragment, mapinfoFragment);
        fragmentTransaction.commit();
    }





/*
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
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } //프래그먼트로 이용시 this 4개를 getActivity()로 수정할것
        gps.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                1000, //  최소 시간간격 (miliSecond)
                1, //  최소 변경거리 (m)
                mLocationListener);
    }*/


}