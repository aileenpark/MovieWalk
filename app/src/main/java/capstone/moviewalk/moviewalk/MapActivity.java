package capstone.moviewalk.moviewalk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    TMapView tmapview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("First Activity");

        Intent intent = getIntent();
        Double name = intent.getDoubleExtra("latitude",-1);
        Double email = intent.getDoubleExtra("longitude",-1);

        TextView mResultTv = findViewById(R.id.resultTv);
        mResultTv.setText("Latitude : " + name + "\nLongitude: " + email);



        ////////////맵나타내기
        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        tmapview = new TMapView(this);
        tmapview.setHttpsMode(true);
        tmapview.setSKTMapApiKey( "b9127345-6c9a-4ada-ba0d-ed9ac7ee23ad");
        linearLayoutTmap.addView(tmapview);

        ////////////마커찍기
        final ArrayList alTMapPoint = new ArrayList();
        alTMapPoint.add(new TMapPoint(name, email));

        final Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.red_pin);


        for (int i = 0; i < alTMapPoint.size(); i++) {
            TMapMarkerItem tItem = new TMapMarkerItem();  // 마커 아이콘 지정
            tItem.setIcon(bitmap);                       // 마커의 좌표 지정
            tItem.setTMapPoint((TMapPoint) alTMapPoint.get(i));       // 지도에 마커 추가
            tmapview.addMarkerItem("markerItem" + i, tItem);

        }
    }
}

