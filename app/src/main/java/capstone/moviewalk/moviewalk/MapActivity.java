package capstone.moviewalk.moviewalk;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    TMapView tmapview;

    LinearLayout ll_fragment;


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



        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        tmapview = new TMapView(this);
        //현재위치 아이콘 표시
        tmapview.setIconVisibility(true);
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

                    mapinfoFragment.changeButton(); // website버튼 활성화
                }
            });


            replaceFragment();
        }
    }

    public void replaceFragment() {
        MapinfoFragment mapinfoFragment = new MapinfoFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll_fragment, mapinfoFragment);
        fragmentTransaction.commit();
    }
}