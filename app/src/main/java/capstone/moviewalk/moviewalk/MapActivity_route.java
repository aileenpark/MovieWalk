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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MapActivity_route extends AppCompatActivity {

    TMapView tmapview;

    Double[][] LatLong = new Double[20][2];
    Double[][] Distance;
    int count2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_route);

        LinearLayout linearLayoutTmap_route = (LinearLayout) findViewById(R.id.linearLayoutTmap_route);

        Intent intent = new Intent(this.getIntent());

        tmapview = new TMapView(this);
        tmapview.setHttpsMode(true);
        tmapview.setSKTMapApiKey("a9ccc80c-1188-4fde-a5e0-627c5a650bcc");
        linearLayoutTmap_route.addView(tmapview);

        //현재위치 아이콘 표시
        tmapview.setIconVisibility(true);
        //gps함수호출
        setGps();
        //현재 보는 방향
        //tmapview.setCompassMode(true);


        //double latitude_route = intent.getDoubleExtra("latitude",-1);
        //double longitude_route = intent.getDoubleExtra("longitude",-1);

        //double latlong = intent.getDoubleExtra("latlong",-1);


        System.out.println("hi");
        String[] Lat_route = intent.getStringArrayExtra("lat");
        String[] Long_route = intent.getStringArrayExtra("long");
        int num = intent.getIntExtra("num",-1);

        System.out.println("hi");
        System.out.println(Lat_route[0] + Long_route[0]);

        //final Double latitude = Double.parseDouble(dataList.get(position).getMember_latitude());
        //final Double longitude = Double.parseDouble(dataList.get(position).getMember_longitude());

        double[] Latitude_route = new double[20];
        double[] Longitude_route = new double[20];

        for(int i=0; i<num; i++) {
            Latitude_route[i] = Double.parseDouble(Lat_route[i]);
            Longitude_route[i] = Double.parseDouble(Long_route[i]);
        }



        //맵 중심좌표 얻어내기
        TMapPoint tpoint = tmapview.getCenterPoint();
        double Latitude_center = tpoint.getLatitude();
        double Longitude_center = tpoint.getLongitude();
/*
        //(1,0),(1,1),(2,0),(2,1) .... 에 위도 경도 대입
        for(int i=0; i<num; i++) {
            LatLong[i+1][0] = Latitude_route[i];
            LatLong[i+1][1] = Longitude_route[i];
        }

        // (0,0),(0,1)에 맵 center포인트 대입
        LatLong[0][0] = Latitude_center;
        LatLong[0][1] = Longitude_center;


        Distance= new Double[num][num];

        for(int k=0;k<num;k++){
            //[k][2]에 계산된 거리들 넣음.
            Distance[k+1][2] = getDistance(LatLong[0][0],LatLong[0][1],LatLong[k+1][0],LatLong[k+1][1]);
            System.out.println(Distance[k][2]);
        }
*/


        /*
        for(int i=0; i<num-1; i++){
            for(int j=0; j<num-i-1; j++){
                if(Distance[i][2] > Distance[i+1][2]){
                    Double a=Distance[i][2];
                    Distance[i][2] = Distance[i+1][2];
                    Distance[i+1][2] = a;
                }
            }
        }
        */


        /////////
        //경로표시
        /////////

        // Tmap API 에서 제공되는 경유지는 총 5개이다.
        // 출발지1, 경유지5, 도착지1 총 7개의 값만 불러올 수 있다.

        TMapPoint startPoint = new TMapPoint(Latitude_route[0], Longitude_route[0]); // 출발지
        TMapPoint endPoint = new TMapPoint(Latitude_route[0], Longitude_route[0]); // 도착지

        TMapPoint point[] = new TMapPoint[20];
        ArrayList<TMapPoint> passList = new ArrayList<TMapPoint>();

        for (int i = 1; i < num; i++) {
            point[i] = new TMapPoint(Latitude_route[i], Longitude_route[i]);
            passList.add(point[i]);
        }

        tmapview.removeTMapPath();
        tmapview.setTrackingMode(true);

        TMapData tmapdata = new TMapData();
        tmapdata.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, startPoint, endPoint, passList, 10, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(5);
                tmapview.addTMapPath(tMapPolyLine);
            }
        });

        Bitmap start = BitmapFactory.decodeResource(getResources(), R.drawable.poi_dot);
        Bitmap end = BitmapFactory.decodeResource(getResources(), R.drawable.end);

        tmapview.setTMapPathIcon(start, end);
        tmapview.zoomToTMapPoint(startPoint, endPoint);

        //////////////
        ////경료표시 끝
        //////////////



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


    public double getDistance(Double lat1, Double lon1, Double lat2, Double lon2){
        return Math.sqrt((lat1-lat2)*(lat1-lat2)+(lon1-lon2)*(lon1-lon2));
    }

}
