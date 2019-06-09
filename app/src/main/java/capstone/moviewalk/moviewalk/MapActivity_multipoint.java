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

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MapActivity_multipoint extends AppCompatActivity {

    TMapView tmapview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_multipoint);

        LinearLayout linearLayoutTmap_multipoint = (LinearLayout) findViewById(R.id.linearLayoutTmap_multipoint);

        Intent intent = new Intent(this.getIntent());

        tmapview = new TMapView(this);
        tmapview.setHttpsMode(true);
        tmapview.setSKTMapApiKey("a9ccc80c-1188-4fde-a5e0-627c5a650bcc");
        linearLayoutTmap_multipoint.addView(tmapview);

        tmapview.setZoomLevel(9);



        String[] Lat_multi = intent.getStringArrayExtra("lat_multipoint");
        String[] Long_multi = intent.getStringArrayExtra("long_multipoint");
        String[] name_multi = intent.getStringArrayExtra("name_multipoint");
        String[] address_multi = intent.getStringArrayExtra("address_multipoint");
        String[] information_multi = intent.getStringArrayExtra("information_multipoint");
        String[] info_URL_multi = intent.getStringArrayExtra("info_URL_multipoint");

        int num_multipoint = intent.getIntExtra("num_multipoint", -1);


        double[] Latitude_multipoint = new double[20];
        double[] Longitude_multipoint = new double[20];
        String[] name_multipoint = new String[20];
        final String[] address_multipoint = new String[20];
        final String[] information_multipoint = new String[20];
        final String[] info_URL_multipoint = new String[20];



        //String 형식인 Lat_route, Long_route를 double형으로
        for (int i = 0; i < num_multipoint; i++) {
            Latitude_multipoint[i] = Double.parseDouble(Lat_multi[i]);
            Longitude_multipoint[i] = Double.parseDouble(Long_multi[i]);
            name_multipoint[i] = name_multi[i];
            address_multipoint[i] = address_multi[i];
            information_multipoint[i] = information_multi[i];
            info_URL_multipoint[i] = info_URL_multi[i];
        }




        TMapPoint point[] = new TMapPoint[100];
        final ArrayList pointList = new ArrayList();
        for (int i = 0; i < num_multipoint; i++) {
            point[i] = new TMapPoint(Latitude_multipoint[i], Longitude_multipoint[i]);
            pointList.add(point[i]);
        }


        final Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.poi_dot);
        //Bitmap bitmap_i = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.menu);

        for (int i = 0; i < pointList.size(); i++) { //다중마커생성
            ////마커표시////
            final TMapMarkerItem tItem = new TMapMarkerItem();  // 마커 아이콘 지정
            tItem.setIcon(bitmap);                       // 마커의 좌표 지정

            tItem.setTMapPoint((TMapPoint) pointList.get(i));       // 지도에 마커 추가
            tmapview.addMarkerItem("markerItem" + i, tItem);

            tItem.setCalloutTitle(name_multipoint[i]);
            //.setCalloutRightButtonImage(bitmap_i);
            tItem.setCanShowCallout(true);
            tItem.setAutoCalloutVisible(true);

/*
            final int k = i;
            tmapview.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
                @Override
                public void onCalloutRightButton(TMapMarkerItem markerItem) {
                    MapinfoFragment mapinfoFragment2 = (MapinfoFragment) getFragmentManager().findFragmentById(R.id.container);
                    mapinfoFragment2.changeFragmentTextView(information_multipoint[k]);
                    mapinfoFragment2.changeFragmentURL(info_URL_multipoint[k]); // website 버튼에 infoURL_map 넘기기
                    }
            });

            */
        }
    }


}