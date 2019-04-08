package capstone.moviewalk.moviewalk;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.logging.LogManager;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.logging.LogManager;

public class MainActivity extends AppCompatActivity {

    TMapView tmapview;
    TMapData tmapdata = new TMapData();


    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        //final
        tmapview = new TMapView(this);
        tmapview.setHttpsMode(true);

        tmapview.setSKTMapApiKey("a9ccc80c-1188-4fde-a5e0-627c5a650bcc");
        linearLayoutTmap.addView(tmapview);






//다중마커//
        final ArrayList alTMapPoint = new ArrayList();
        alTMapPoint.add(new TMapPoint(37.505426, 126.957169));
        alTMapPoint.add(new TMapPoint(37.506426, 126.958169));
        alTMapPoint.add(new TMapPoint(37.507426, 126.959169));
        alTMapPoint.add(new TMapPoint(37.508426, 126.960169));
        alTMapPoint.add(new TMapPoint(37.509426, 126.961169));
        alTMapPoint.add(new TMapPoint(37.510426, 126.962169));
        alTMapPoint.add(new TMapPoint(37.511426, 126.963169));
        final Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.red_pin);


        for(int i=0; i<alTMapPoint.size(); i++) {
            TMapMarkerItem tItem = new TMapMarkerItem();  // 마커 아이콘 지정
            tItem.setIcon(bitmap);                       // 마커의 좌표 지정
            tItem.setTMapPoint((TMapPoint) alTMapPoint.get(i));       // 지도에 마커 추가
            tmapview.addMarkerItem("markerItem" + i, tItem);


            //풍선뷰 오른쪽 에 사용될 이미지 설정
            //Bitmap bitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.blue_pin);
            //tItem.setCalloutRightButtonImage(bitmap2);
        }






//인텐트 연습
/*        btn_next = (Button)findViewById(R.id.btn_next);
        btn_next .setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //다음페이지로 화면 전환
                //화면 전환할때 사용하는 클래스

                //두개의 파라미터(이동 전 activity, 이동 할 activity)
                Intent intent=new Intent(MainActivity.this, SubActivity.class);
                //화면 전환하기
                startActivity(intent);

            }
        });
*/



        //마커의 ID 반환
        // String MarkerID = tItem.getID();
        //ID값으로 해당 마커 반환
        //  TMapMarkerItem markeritem = tmapview.getMarkerItemFromID("tItem");


        //지도 이벤트 설정(마커)
        // 클릭 이벤트 설정
        tmapview.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {

            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {

                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                TMapMarkerItem marker = null;
                for(int i=0; i<arrayList.size(); i++) {
                    marker = arrayList.get(i);


                    if (!arrayList.get(0).equals(null)) {
                        String markerID = marker.getID();

                        Intent intent = new Intent(MainActivity.this, SubActivity.class);
                        intent.putExtra("markerID", markerID);
                        startActivity(intent);
                    }
                }
                return false;
            }
        });

// 롱 클릭 이벤트 설정
        tmapview.setOnLongClickListenerCallback(new TMapView.OnLongClickListenerCallback() {
            @Override
            public void onLongPressEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint) {
                //Toast.makeText(MapEvent.this, "onLongPress~!", Toast.LENGTH_SHORT).show();
            }
        });

// 지도 스크롤 종료
        tmapview.setOnDisableScrollWithZoomLevelListener(new TMapView.OnDisableScrollWithZoomLevelCallback() {
            @Override
            public void onDisableScrollWithZoomLevelEvent(float zoom, TMapPoint centerPoint) {
                //Toast.makeText(MapEvent.this, "zoomLevel=" + zoom + "\nlon=" + centerPoint.getLongitude() + "\nlat=" + centerPoint.getLatitude(), Toast.LENGTH_SHORT).show();
            }
        });










        Button buttonMove1 = (Button) findViewById(R.id.buttonMove1);
        Button buttonMove2 = (Button) findViewById(R.id.buttonMove2);
        Button buttonZoomIn = (Button) findViewById(R.id.buttonZoomIn);
        Button buttonZoomOut = (Button) findViewById(R.id.buttonZoomOut);
        Button buttonZoomLevel10 = (Button) findViewById(R.id.buttonZoomLevel10);
// "N서울타워" 버튼 클릭
        buttonMove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3번째 파라미터 생략 == 지도 이동 Animation 사용안함
                tmapview.setCenterPoint(126.988205, 37.551135);
            }
        });
// "중앙대학교" 버튼 클릭
        buttonMove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3번째 파라미터 true == 지도 이동 Animation 사용
                tmapview.setCenterPoint(126.957069, 37.505326, true);
            }
        });
// "확대" 버튼 클릭
        buttonZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmapview.MapZoomIn();
            }
        });
// "축소" 버튼 클릭
        buttonZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmapview.MapZoomOut();
            }
        });
// "ZoomLevel=10" 버튼 클릭
        buttonZoomLevel10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmapview.setZoomLevel(10);
            }
        });

        //현재위치 표시
        tmapview.setIconVisibility(true);
        setGps();
    }






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

    public void setGps() {
        final LocationManager gps = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        gps.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                1000, //  최소 시간간격 (miliSecond)
                1, //  최소 변경거리 (m)
                mLocationListener);
    }
}