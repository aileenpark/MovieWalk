package capstone.moviewalk.moviewalk;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MapActivity_route extends AppCompatActivity {

    TMapView tmapview;
    TMapGpsManager gps=null;

    Double[][] LatLong = new Double[20][2];
    Double[][] Distance;
    int count2 = 0;


    private ArrayList<GPS> route_GPS = new ArrayList<GPS>();

    public void addPoint(){
        route_GPS.add(new GPS(latitude, longitude));
    }

    ///시도해본것///
    double latitude;
    double longitude;
/*
    public void setLatitude(double latitude){
        this.latitude=latitude;
    }
    public double getLatitude2(){
        return this.latitude;
    }

    public void setLongitude(double longitude){
        this.longitude=longitude;
    }
    public double getLongitude2(){
        return this.longitude;
    }

    public MapActivity_route(){    }
   */
    //////

    ///////gps 표시
    ///////gps 를 통해 위도, 경도 추출 가능
    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            if (location != null) {
                latitude = location.getLatitude(); //현재 위도 latitude에 저장
                longitude = location.getLongitude(); //현재 경도 longitude에 저장
                tmapview.setLocationPoint(longitude, latitude); //tmapview의 위도, 경도 설정
                tmapview.setCenterPoint(longitude, latitude); //tmapview의 중심점 위도, 경도로 설정

                System.out.println("#################################################");
                System.out.println(latitude + longitude);
                System.out.println(latitude + longitude);
                System.out.println(latitude + longitude);
                System.out.println(latitude + longitude);
                System.out.println(latitude + longitude);
                System.out.println("#################################################");

               // setLatitude(latitude);
               // setLongitude(longitude);
            }
        }

        public void onProviderDisabled(String provider) {        }

        public void onProviderEnabled(String provider) {        }

        public void onStatusChanged(String provider, int status, Bundle extras) {        }
    };


    /////gps표시
    public void setGps() {
        //acquire a reference to the system location manager
        final LocationManager gps = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } //프래그먼트로 이용시 this 4개를 getActivity()로 수정할것
        gps.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                100, //  최소 시간간격 (miliSecond)
                10, //  최소 변경거리 (m)
                mLocationListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_route);

        LinearLayout linearLayoutTmap_route = (LinearLayout) findViewById(R.id.linearLayoutTmap_route);

        addPoint();

        ///시도해본것///
        //MapActivity_route location = new MapActivity_route();
        //double latitude2 = location.();
        //double longitude2 = location.getLongitude2();
        //////


        Intent intent = new Intent(this.getIntent());

        tmapview = new TMapView(this);
        tmapview.setHttpsMode(true);
        tmapview.setSKTMapApiKey("a9ccc80c-1188-4fde-a5e0-627c5a650bcc");
        linearLayoutTmap_route.addView(tmapview);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        setGps();


        /*
        gps = new TMapGpsManager(this);
        gps.setMinTime(1000);
        gps.setMinDistance(5);
        gps.setProvider(gps.GPS_PROVIDER); //현재위치 찾을 방법(휴대폰 gps)
        //gps.setProvider(gps.NETWORK_PROVIDER);
        gps.OpenGps(); //gps 위치 탐색 허용

        setlocationthread();
*/




        //현재위치 아이콘 표시
        tmapview.setIconVisibility(true);


       // TMapPoint ST = gps.getLocation();

        String[] Lat_route = intent.getStringArrayExtra("lat");
        String[] Long_route = intent.getStringArrayExtra("long");
        int num = intent.getIntExtra("num",-1);


        //final Double latitude = Double.parseDouble(dataList.get(position).getMember_latitude());
        //final Double longitude = Double.parseDouble(dataList.get(position).getMember_longitude());

        double[] Latitude_route = new double[20];
        double[] Longitude_route = new double[20];

        //num: 데이터 개수
        for(int i=0; i<num; i++) {
            Latitude_route[i] = Double.parseDouble(Lat_route[i]);
            Longitude_route[i] = Double.parseDouble(Long_route[i]);
            System.out.println(i);//0 1 2 3
        }



        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable(){
            public void run(){
            }
        },5000);



        //맵 중심좌표 얻어내기
        TMapPoint tpoint = tmapview.getCenterPoint();
        //TMapPoint tpoint = new TMapPoint();
        double Latitude_center = tpoint.getLatitude();
        double Longitude_center = tpoint.getLongitude();


        TMapPoint point[] = new TMapPoint[20];
        ArrayList<TMapPoint> passList = new ArrayList<TMapPoint>();

        for (int i = 0; i < num; i++) {
            point[i] = new TMapPoint(Latitude_route[i], Longitude_route[i]);
        }

        // 0 1 2 3

        /////////////////////////////////////////////
        //원래는 사용자 위치 받아야 함
        //TMapPoint examplePoint = new TMapPoint(37.506036,126.9566235);
        //TMapPoint examplePoint = new TMapPoint(Latitude_center, Longitude_center);
        TMapPoint examplePoint = new TMapPoint(latitude, longitude);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(this.latitude + this.longitude);
       // System.out.println(getLatitude2()+getLongitude2());
        System.out.println(latitude+longitude);
        //System.out.println(latitude2+longitude2);
        System.out.println(Latitude_center+Longitude_center);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        TMapPolyLine polyEx = new TMapPolyLine();
        double Distance=0;
        double Distance2=9999999999.9;

        int idx=-1;//현재 사용자 위치에서 제일 가까운 점 찾음


        for(int i =0; i<num;i++){
            polyEx.addLinePoint(examplePoint);
            polyEx.addLinePoint(point[i]);
            Distance = polyEx.getDistance();
            System.out.println(Distance);
            if(Distance<Distance2){
                Distance2=Distance;
                System.out.println(Distance2);
                idx++;
            }
        }

        for(int i=0;i<num;i++){
            if(i+idx<num){
                System.out.println(i+idx);
                passList.add(point[i+idx]);
            }
            else{
                System.out.println(i-idx);
                passList.add(point[i-idx]);
            }
        }

         ////////////////////////////////////
        tmapview.removeTMapPath();
        tmapview.setTrackingMode(true);

        TMapData tmapdata = new TMapData();
        tmapdata.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, examplePoint, examplePoint, passList, 10, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(10);
                tmapview.addTMapPath(tMapPolyLine);
            }
        });

        Bitmap start = BitmapFactory.decodeResource(getResources(), R.drawable.poi_dot);
        //Bitmap end = BitmapFactory.decodeResource(getResources(), R.drawable.end);
        Bitmap end = BitmapFactory.decodeResource(getResources(), R.drawable.poi_dot);
        tmapview.setTMapPathIcon(start, end);



    }


/*
    private void setlocationthread(){
        gps.setProvider(TMapGpsManager.GPS_PROVIDER);
        gps.OpenGps();

        gpsLocationthread();

        if(gps.getLocation().getLongitude()==0){
            Toast.makeText(getApplicationContext(), "다시 시도하세요", Toast.LENGTH_SHORT).show();
        }
        else
            tmapview.setCenterPoint(gps.getLocation().getLongitude(), gps.getLocation().getLatitude());

        gps.CloseGps();
    }

    private void gpsLocationthread(){
        for(int i=0; i<10; i++){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                setlocationthread();
            }
        }
    }
*/


}
